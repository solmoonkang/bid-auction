package com.bid.auction.auth.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bid.auction.auth.application.dto.request.LoginRequest;
import com.bid.auction.auth.application.dto.session.SessionMember;
import com.bid.auction.auth.application.fixture.AuthFixture;
import com.bid.auction.global.error.exception.BadRequestException;
import com.bid.auction.global.error.model.ErrorCode;
import com.bid.auction.member.application.component.MemberFinder;
import com.bid.auction.member.application.validator.MemberValidator;
import com.bid.auction.member.domain.model.Member;
import com.bid.auction.member.fixture.MemberFixture;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

	@Mock
	private MemberFinder memberFinder;

	@Mock
	private MemberValidator memberValidator;

	@InjectMocks
	private LoginService loginService;

	@Test
	@DisplayName("LOGIN - [SUCCESS] 올바른 정보를 입력하여 로그인에 성공했습니다.")
	void login_SessionMember_success() {
		// GIVEN
		LoginRequest loginRequest = AuthFixture.aLoginRequest();
		Member member = MemberFixture.aMember();

		given(memberFinder.findByEmail(anyString())).willReturn(member);

		// WHEN
		SessionMember sessionMember = loginService.login(loginRequest);

		// THEN
		assertThat(sessionMember).isNotNull();
		assertThat(sessionMember.email()).isEqualTo(member.getEmail());

		verify(memberFinder).findByEmail(eq(loginRequest.email()));
		verify(memberValidator).validatePassword(
			eq(loginRequest.password()),
			eq(member.getPassword()),
			eq(ErrorCode.LOGIN_FAILED)
		);
	}

	@Test
	@DisplayName("LOGIN - [FAILURE] 존재하지 않는 이메일로 요청하여 로그인에 실패했습니다.")
	void login_SessionMember_emailNotFound_BadRequestException_failure() {
		// GIVEN
		String notFoundEmail = "notFound@example.com";
		LoginRequest loginRequest = AuthFixture.aLoginRequestWithEmail(notFoundEmail);

		given(memberFinder.findByEmail(anyString()))
			.willThrow(new BadRequestException(ErrorCode.LOGIN_FAILED));

		// WHEN & THEN
		assertThatThrownBy(() -> loginService.login(loginRequest))
			.isInstanceOf(BadRequestException.class)
			.hasFieldOrPropertyWithValue("errorCode", ErrorCode.LOGIN_FAILED);

		verify(memberValidator, never()).validatePassword(anyString(), anyString(), any());
	}

	@Test
	@DisplayName("LOGIN - [FAILURE] 비밀번호가 일치하지 않아 로그인에 실패했습니다.")
	void login_SessionMember_passwordMismatch_BadRequestException_failure() {
		// GIVEN
		LoginRequest loginRequest = AuthFixture.aLoginRequestWithWrongPassword();
		Member member = MemberFixture.aMember();

		given(memberFinder.findByEmail(anyString())).willReturn(member);

		doThrow(new BadRequestException(ErrorCode.LOGIN_FAILED))
			.when(memberValidator)
			.validatePassword(eq(loginRequest.password()), eq(member.getPassword()), eq(ErrorCode.LOGIN_FAILED));

		// WHEN & THEN
		assertThatThrownBy(() -> loginService.login(loginRequest))
			.isInstanceOf(BadRequestException.class)
			.hasFieldOrPropertyWithValue("errorCode", ErrorCode.LOGIN_FAILED);

		verify(memberFinder).findByEmail(eq(loginRequest.email()));
		verify(memberValidator).validatePassword(
			eq(loginRequest.password()),
			eq(member.getPassword()),
			eq(ErrorCode.LOGIN_FAILED)
		);
	}
}
