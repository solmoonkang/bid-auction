package com.bid.auction.member.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bid.auction.global.error.exception.BadRequestException;
import com.bid.auction.global.error.exception.NotFoundException;
import com.bid.auction.global.error.model.ErrorCode;
import com.bid.auction.member.application.component.MemberCacheProcessor;
import com.bid.auction.member.application.component.MemberFinder;
import com.bid.auction.member.application.dto.request.WithdrawalRequest;
import com.bid.auction.member.application.validator.MemberValidator;
import com.bid.auction.member.domain.model.Member;
import com.bid.auction.member.fixture.MemberFixture;

@ExtendWith(MockitoExtension.class)
class WithdrawalServiceTest {

	@Mock
	private MemberFinder memberFinder;

	@Mock
	private MemberValidator memberValidator;

	@Mock
	private MemberCacheProcessor memberCacheProcessor;

	@Mock
	private Clock clock;

	@InjectMocks
	private WithdrawalService withdrawalService;

	private final Instant fixedInstant = Instant.parse("2026-04-13T10:00:00Z");
	private final ZoneId zoneId = ZoneId.systemDefault();

	@Test
	@DisplayName("WITHDRAW - [SUCCESS] 등록된 회원이 탈퇴하면 상태가 변경되고 캐시가 삭제됩니다.")
	void withdraw_void_success() {
		// GIVEN
		given(clock.instant()).willReturn(fixedInstant);
		given(clock.getZone()).willReturn(zoneId);

		Long memberId = 999L;
		Member member = MemberFixture.aMember();
		String previousEncodedPassword = member.getPassword();

		WithdrawalRequest withdrawalRequest = MemberFixture.aWithdrawalRequest();
		String previousEmail = member.getEmail();

		given(memberFinder.findById(memberId)).willReturn(member);

		// WHEN
		withdrawalService.withdraw(memberId, withdrawalRequest);

		// THEN
		assertThat(member.isWithdrawn()).isTrue();

		verify(memberValidator).validatePassword(
			eq(withdrawalRequest.password()),
			eq(previousEncodedPassword),
			eq(ErrorCode.WRONG_PASSWORD)
		);
		verify(memberCacheProcessor).evictMemberCache(eq(memberId), eq(previousEmail));
		verify(memberCacheProcessor).evictMemberCache(eq(memberId), eq(member.getEmail()));
	}

	@Test
	@DisplayName("WITHDRAW - [FAILURE] 비밀번호가 일치하지 않아 회원 탈퇴에 실패했습니다.")
	void withdraw_void_wrongPassword_BadRequestException_failure() {
		// GIVEN
		Long memberId = 999L;
		Member member = MemberFixture.aMember();
		WithdrawalRequest withdrawalRequest = MemberFixture.aWithdrawalRequest();

		given(memberFinder.findById(memberId)).willReturn(member);

		doThrow(new BadRequestException(ErrorCode.WRONG_PASSWORD))
			.when(memberValidator)
			.validatePassword(anyString(), anyString(), any(ErrorCode.class));

		// WHEN & THEN
		assertThatThrownBy(() -> withdrawalService.withdraw(memberId, withdrawalRequest))
			.isInstanceOf(BadRequestException.class)
			.extracting(exception -> ((BadRequestException)exception).getErrorCode())
			.isEqualTo(ErrorCode.WRONG_PASSWORD);

		assertThat(member.isWithdrawn()).isFalse();
		verify(memberCacheProcessor, never()).evictMemberCache(anyLong(), anyString());
	}

	@Test
	@DisplayName("WITHDRAW - [FAILURE] 존재하지 않는 회원 ID로 조회하여 회원 탈퇴에 실패했습니다.")
	void withdraw_void_memberNotFound_NotFoundException_failure() {
		// GIVEN
		Long memberId = 999L;
		WithdrawalRequest withdrawalRequest = MemberFixture.aWithdrawalRequest();

		given(memberFinder.findById(memberId)).willThrow(new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));

		// WHEN & THEN
		assertThatThrownBy(() -> withdrawalService.withdraw(memberId, withdrawalRequest))
			.isInstanceOf(NotFoundException.class)
			.extracting(exception -> ((NotFoundException)exception).getErrorCode())
			.isEqualTo(ErrorCode.MEMBER_NOT_FOUND);

		verify(memberCacheProcessor, never()).evictMemberCache(anyLong(), anyString());
	}
}
