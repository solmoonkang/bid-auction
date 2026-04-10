package com.bid.auction.member.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bid.auction.global.error.exception.ConflictException;
import com.bid.auction.global.error.model.ErrorCode;
import com.bid.auction.member.application.component.MemberCacheProcessor;
import com.bid.auction.member.application.component.MemberFinder;
import com.bid.auction.member.application.dto.request.EmailUpdateRequest;
import com.bid.auction.member.application.validator.MemberValidator;
import com.bid.auction.member.domain.model.Member;
import com.bid.auction.member.fixture.MemberFixture;

@ExtendWith(MockitoExtension.class)
class ProfileUpdateServiceTest {

	@Mock
	private MemberFinder memberFinder;

	@Mock
	private MemberValidator memberValidator;

	@Mock
	private MemberCacheProcessor memberCacheProcessor;

	@InjectMocks
	private ProfileUpdateService profileUpdateService;

	@Test
	@DisplayName("UPDATE EMAIL - [SUCCESS] 새로운 이메일로 수정하고 캐시 갱신에 성공했습니다.")
	void updateEmail_void_success() {
		// GIVEN
		Long memberId = 999L;
		String oldEmail = "old@example.com";
		String newEmail = "new@example.com";

		Member member = MemberFixture.aMemberWithEmail(oldEmail);
		EmailUpdateRequest emailUpdateRequest = MemberFixture.aEmailUpdateRequest(newEmail);

		given(memberFinder.findById(memberId)).willReturn(member);

		// WHEN
		profileUpdateService.updateEmail(memberId, emailUpdateRequest);

		// THEN
		assertThat(member.getEmail()).isEqualTo(newEmail);

		verify(memberValidator).validateEmailUniqueness(eq(newEmail));
		verify(memberCacheProcessor).evictMemberCache(eq(memberId), eq(oldEmail));
		verify(memberCacheProcessor).evictMemberCache(eq(memberId), eq(newEmail));
	}

	@Test
	@DisplayName("UPDATE EMAIL - [SUCCESS] 요청한 이메일이 기존 이메일과 동일하면 로직을 종료합니다.")
	void updateEmail_void_sameEmail_success() {
		// GIVEN
		Long memberId = 999L;
		String sameEmail = "same@example.com";

		Member member = MemberFixture.aMemberWithEmail(sameEmail);
		EmailUpdateRequest emailUpdateRequest = MemberFixture.aEmailUpdateRequest(sameEmail);

		given(memberFinder.findById(memberId)).willReturn(member);

		// WHEN
		profileUpdateService.updateEmail(memberId, emailUpdateRequest);

		// THEN
		verify(memberValidator, never()).validateEmailUniqueness(anyString());
		verify(memberCacheProcessor, never()).evictMemberCache(anyLong(), anyString());
	}

	@Test
	@DisplayName("UPDATE EMAIL - [FAILURE] 해당 이메일은 이미 등록되어 새로운 이메일 업데이트에 실패했습니다.")
	void updateEmail_void_emailDuplicated_ConflictException_failure() {
		// GIVEN
		Long memberId = 999L;
		String oldEmail = "old@example.com";
		String newEmail = "duplicate@example.com";

		Member member = MemberFixture.aMemberWithEmail(oldEmail);
		EmailUpdateRequest emailUpdateRequest = MemberFixture.aEmailUpdateRequest(newEmail);

		given(memberFinder.findById(memberId)).willReturn(member);

		// WHEN & THEN
		doThrow(new ConflictException(ErrorCode.EMAIL_DUPLICATION))
			.when(memberValidator).validateEmailUniqueness(eq(newEmail));

		assertThatThrownBy(() -> profileUpdateService.updateEmail(memberId, emailUpdateRequest))
			.isInstanceOf(ConflictException.class)
			.extracting(exception -> ((ConflictException)exception).getErrorCode())
			.isEqualTo(ErrorCode.EMAIL_DUPLICATION);

		assertThat(member.getEmail()).isNotEqualTo(newEmail);
		verify(memberCacheProcessor, never()).evictMemberCache(anyLong(), anyString());
	}
}
