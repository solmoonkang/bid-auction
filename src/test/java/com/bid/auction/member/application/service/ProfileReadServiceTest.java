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

import com.bid.auction.global.error.exception.NotFoundException;
import com.bid.auction.global.error.model.ErrorCode;
import com.bid.auction.member.application.component.MemberFinder;
import com.bid.auction.member.application.dto.response.ProfileResponse;
import com.bid.auction.member.application.mapper.MemberResponseMapper;
import com.bid.auction.member.domain.model.Member;
import com.bid.auction.member.fixture.MemberFixture;

@ExtendWith(MockitoExtension.class)
class ProfileReadServiceTest {

	@Mock
	private MemberFinder memberFinder;

	@InjectMocks
	private ProfileReadService profileReadService;

	@Test
	@DisplayName("GET PROFILE - [SUCCESS] 등록된 회원임이 인증되어 내 프로필 정보 조회에 성공했습니다.")
	void getProfile_ProfileResponse_success() {
		// GIVEN
		Long memberId = 999L;
		Member member = MemberFixture.aMember();
		ProfileResponse expectedProfileResponse = MemberResponseMapper.toProfileResponse(member);

		given(memberFinder.findById(memberId)).willReturn(member);

		// WHEN
		ProfileResponse actualProfileResponse = profileReadService.getProfile(memberId);

		// THEN
		assertThat(actualProfileResponse)
			.usingRecursiveComparison()
			.isEqualTo(expectedProfileResponse);

		verify(memberFinder).findById(eq(memberId));
	}

	@Test
	@DisplayName("GET PROFILE - [FAILURE] 존재하지 않는 회원 ID로 조회하여 내 프로필 정보 조회에 실패했습니다.")
	void getProfile_ProfileResponse_memberNotFound_NotFoundException_failure() {
		// GIVEN
		Long memberId = 999L;

		given(memberFinder.findById(memberId))
			.willThrow(new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));

		// WHEN & THEN
		assertThatThrownBy(() -> profileReadService.getProfile(memberId))
			.isInstanceOf(NotFoundException.class)
			.extracting(exception -> ((NotFoundException)exception).getErrorCode())
			.isEqualTo(ErrorCode.MEMBER_NOT_FOUND);

		verify(memberFinder).findById(eq(memberId));
	}
}
