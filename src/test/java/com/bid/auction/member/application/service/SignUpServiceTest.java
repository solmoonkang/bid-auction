package com.bid.auction.member.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bid.auction.global.error.exception.BadRequestException;
import com.bid.auction.global.error.exception.ConflictException;
import com.bid.auction.global.error.model.ErrorCode;
import com.bid.auction.member.application.dto.request.SignUpRequest;
import com.bid.auction.member.application.validator.MemberValidator;
import com.bid.auction.member.domain.model.Member;
import com.bid.auction.member.domain.repository.MemberRepository;
import com.bid.auction.member.fixture.MemberFixture;

@ExtendWith(MockitoExtension.class)
class SignUpServiceTest {

	@Mock
	private MemberRepository memberRepository;

	@Mock
	private MemberValidator memberValidator;

	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private SignUpService signUpService;

	@Test
	@DisplayName("SIGN UP - [SUCCESS] 올바른 회원 정보를 입력하여 신규 사용자 등록에 성공했습니다.")
	void signUp_void_success() {
		// GIVEN
		SignUpRequest signUpRequest = MemberFixture.aSignUpRequest();
		given(passwordEncoder.encode(anyString())).willReturn("encodedPassword");

		// WHEN
		signUpService.signUp(signUpRequest);

		// THEN
		verify(memberValidator).validatePasswordConfirmation(anyString(), anyString());
		verify(memberValidator).validateUniqueness(anyString(), anyString(), anyString());

		ArgumentCaptor<Member> memberArgumentCaptor = ArgumentCaptor.forClass(Member.class);
		verify(memberRepository).save(memberArgumentCaptor.capture());

		Member savedMember = memberArgumentCaptor.getValue();
		assertThat(savedMember.getPassword()).isEqualTo("encodedPassword");
		assertThat(savedMember.getEmail()).isEqualTo(signUpRequest.email());
	}

	@Test
	@DisplayName("SIGN UP - [FAILURE] 비밀번호와 확인 비밀번호가 일치하지 않아 신규 사용자 등록에 실패했습니다.")
	void signUp_void_passwordMismatch_BadRequestException_failure() {
		// GIVEN
		SignUpRequest signUpRequest = MemberFixture.aSignUpRequestWithPasswordMismatch();

		doThrow(new BadRequestException(ErrorCode.INVALID_INPUT_VALUE))
			.when(memberValidator).validatePasswordConfirmation(signUpRequest.password(), signUpRequest.checkPassword());

		// WHEN & THEN
		assertThatThrownBy(() -> signUpService.signUp(signUpRequest))
			.isInstanceOf(BadRequestException.class)
			.extracting(exception -> ((BadRequestException)exception).getErrorCode())
			.isEqualTo(ErrorCode.INVALID_INPUT_VALUE);

		verify(passwordEncoder, never()).encode(anyString());
		verify(memberRepository, never()).save(any(Member.class));
	}

	@Test
	@DisplayName("SIGN UP - [FAILURE] 해당 이메일은 이미 등록되어 신규 사용자 등록에 실패했습니다.")
	void signUp_void_emailDuplicated_ConflictException_failure() {
		// GIVEN
		String duplicatedEmail = "duplicate@example.com";
		SignUpRequest signUpRequest = MemberFixture.aSignUpRequestWithEmail(duplicatedEmail);

		doThrow(new ConflictException(ErrorCode.EMAIL_DUPLICATION))
			.when(memberValidator).validateUniqueness(eq(duplicatedEmail), anyString(), anyString());

		// WHEN & THEN
		assertThatThrownBy(() -> signUpService.signUp(signUpRequest))
			.isInstanceOf(ConflictException.class)
			.extracting(exception -> ((ConflictException)exception).getErrorCode())
			.isEqualTo(ErrorCode.EMAIL_DUPLICATION);

		verify(passwordEncoder, never()).encode(anyString());
		verify(memberRepository, never()).save(any(Member.class));
	}

	@Test
	@DisplayName("SIGN UP - [FAILURE] 해당 닉네임은 이미 등록되어 신규 사용자 등록에 실패했습니다.")
	void signUp_void_nicknameDuplicated_ConflictException_failure() {
		// GIVEN
		String duplicatedNickname = "duplicateNickname";
		SignUpRequest signUpRequest = MemberFixture.aSignUpRequestWithNickname(duplicatedNickname);

		doThrow(new ConflictException(ErrorCode.NICKNAME_DUPLICATION))
			.when(memberValidator).validateUniqueness(anyString(), eq(duplicatedNickname), anyString());

		// WHEN & THEN
		assertThatThrownBy(() -> signUpService.signUp(signUpRequest))
			.isInstanceOf(ConflictException.class)
			.extracting(exception -> ((ConflictException)exception).getErrorCode())
			.isEqualTo(ErrorCode.NICKNAME_DUPLICATION);

		verify(passwordEncoder, never()).encode(anyString());
		verify(memberRepository, never()).save(any(Member.class));
	}

	@Test
	@DisplayName("SIGN UP - [FAILURE] 해당 전화번호는 이미 등록되어 신규 사용자 등록에 실패했습니다.")
	void signUp_void_phoneNumberDuplicated_ConflictException_failure() {
		// GIVEN
		String duplicatedPhoneNumber = "duplicatePhoneNumber";
		SignUpRequest signUpRequest = MemberFixture.aSignUpRequestWithPhoneNumber(duplicatedPhoneNumber);

		doThrow(new ConflictException(ErrorCode.PHONE_DUPLICATION))
			.when(memberValidator).validateUniqueness(anyString(), anyString(), eq(duplicatedPhoneNumber));

		// WHEN & THEN
		assertThatThrownBy(() -> signUpService.signUp(signUpRequest))
			.isInstanceOf(ConflictException.class)
			.extracting(exception -> ((ConflictException)exception).getErrorCode())
			.isEqualTo(ErrorCode.PHONE_DUPLICATION);

		verify(passwordEncoder, never()).encode(anyString());
		verify(memberRepository, never()).save(any(Member.class));
	}
}
