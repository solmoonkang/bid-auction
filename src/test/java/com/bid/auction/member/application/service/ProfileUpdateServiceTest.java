package com.bid.auction.member.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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
import com.bid.auction.member.application.dto.request.NicknameUpdateRequest;
import com.bid.auction.member.application.dto.request.PhoneNumberUpdateRequest;
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

	@Nested
	@DisplayName("UPDATE EMAIL - 이메일 수정")
	class UpdateEmail {

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

	@Nested
	@DisplayName("UPDATE NICKNAME - 닉네임 수정")
	class UpdateNickname {

		@Test
		@DisplayName("UPDATE NICKNAME - [SUCCESS] 새로운 닉네임으로 수정하고 캐시 갱신에 성공했습니다.")
		void updateNickname_void_success() {
			// GIVEN
			Long memberId = 999L;
			String oldNickname = "oldNickname";
			String newNickname = "newNickname";

			Member member = MemberFixture.aMemberWithNickname(oldNickname);
			NicknameUpdateRequest nicknameUpdateRequest = MemberFixture.aNicknameUpdateRequest(newNickname);

			given(memberFinder.findById(memberId)).willReturn(member);

			// 	WHEN
			profileUpdateService.updateNickname(memberId, nicknameUpdateRequest);

			// THEN
			assertThat(member.getNickname()).isEqualTo(newNickname);

			verify(memberValidator).validateNicknameUniqueness(eq(newNickname));
			verify(memberCacheProcessor).evictMemberCache(eq(memberId), eq(member.getEmail()));
		}

		@Test
		@DisplayName("UPDATE NICKNAME - [SUCCESS] 요청한 닉네임이 기존 닉네임과 동일하면 로직을 종료합니다.")
		void updateNickname_void_sameNickname_success() {
			// GIVEN
			Long memberId = 999L;
			String sameNickname = "sameNickname";

			Member member = MemberFixture.aMemberWithNickname(sameNickname);
			NicknameUpdateRequest nicknameUpdateRequest = MemberFixture.aNicknameUpdateRequest(sameNickname);

			given(memberFinder.findById(memberId)).willReturn(member);

			// WHEN
			profileUpdateService.updateNickname(memberId, nicknameUpdateRequest);

			// THEN
			verify(memberValidator, never()).validateNicknameUniqueness(anyString());
			verify(memberCacheProcessor, never()).evictMemberCache(anyLong(), anyString());
		}

		@Test
		@DisplayName("UPDATE NICKNAME - [FAILURE] 해당 닉네임은 이미 등록되어 새로운 닉네임 업데이트에 실패했습니다.")
		void updateNickname_void_nicknameDuplicated_ConflictException_failure() {
			// GIVEN
			Long memberId = 999L;
			String oldNickname = "oldNickname";
			String newNickname = "duplicateNickname";

			Member member = MemberFixture.aMemberWithNickname(oldNickname);
			NicknameUpdateRequest nicknameUpdateRequest = MemberFixture.aNicknameUpdateRequest(newNickname);

			given(memberFinder.findById(memberId)).willReturn(member);

			// WHEN & THEN
			doThrow(new ConflictException(ErrorCode.NICKNAME_DUPLICATION))
				.when(memberValidator).validateNicknameUniqueness(eq(newNickname));

			assertThatThrownBy(() -> profileUpdateService.updateNickname(memberId, nicknameUpdateRequest))
				.isInstanceOf(ConflictException.class)
				.extracting(exception -> ((ConflictException)exception).getErrorCode())
				.isEqualTo(ErrorCode.NICKNAME_DUPLICATION);

			assertThat(member.getNickname()).isNotEqualTo(newNickname);
			verify(memberCacheProcessor, never()).evictMemberCache(anyLong(), anyString());
		}
	}

	@Nested
	@DisplayName("UPDATE PHONE NUMBER - 전화번호 수정")
	class UpdatePhoneNumber {

		@Test
		@DisplayName("UPDATE PHONE NUMBER - [SUCCESS] 새로운 전화번호로 수정하고 캐시 갱신에 성공했습니다.")
		void updatePhoneNumber_void_success() {
			// GIVEN
			Long memberId = 999L;
			String oldPhoneNumber = "01023456789";
			String newPhoneNumber = "01045671234";

			Member member = MemberFixture.aMemberWithPhoneNumber(oldPhoneNumber);
			PhoneNumberUpdateRequest phoneNumberUpdateRequest = MemberFixture.aPhoneNumberUpdateRequest(newPhoneNumber);

			given(memberFinder.findById(memberId)).willReturn(member);

			// 	WHEN
			profileUpdateService.updatePhoneNumber(memberId, phoneNumberUpdateRequest);

			// THEN
			assertThat(member.getPhoneNumber()).isEqualTo(newPhoneNumber);

			verify(memberValidator).validatePhoneNumberUniqueness(eq(newPhoneNumber));
			verify(memberCacheProcessor).evictMemberCache(eq(memberId), eq(member.getEmail()));
		}

		@Test
		@DisplayName("UPDATE PHONE NUMBER - [SUCCESS] 요청한 전화번호가 기존 전화번호와 동일하면 로직을 종료합니다.")
		void updatePhoneNumber_void_samePhoneNumber_success() {
			// GIVEN
			Long memberId = 999L;
			String samePhoneNumber = "01023456789";

			Member member = MemberFixture.aMemberWithPhoneNumber(samePhoneNumber);
			PhoneNumberUpdateRequest phoneNumberUpdateRequest = MemberFixture.aPhoneNumberUpdateRequest(
				samePhoneNumber
			);

			given(memberFinder.findById(memberId)).willReturn(member);

			// WHEN
			profileUpdateService.updatePhoneNumber(memberId, phoneNumberUpdateRequest);

			// THEN
			verify(memberValidator, never()).validatePhoneNumberUniqueness(anyString());
			verify(memberCacheProcessor, never()).evictMemberCache(anyLong(), anyString());
		}

		@Test
		@DisplayName("UPDATE PHONE NUMBER - [FAILURE] 해당 전화번호는 이미 등록되어 새로운 전화번호 업데이트에 실패했습니다.")
		void updateNickname_void_nicknameDuplicated_ConflictException_failure() {
			// GIVEN
			Long memberId = 999L;
			String oldPhoneNumber = "01023456789";
			String newPhoneNumber = "01045671234";

			Member member = MemberFixture.aMemberWithPhoneNumber(oldPhoneNumber);
			PhoneNumberUpdateRequest phoneNumberUpdateRequest = MemberFixture.aPhoneNumberUpdateRequest(newPhoneNumber);

			given(memberFinder.findById(memberId)).willReturn(member);

			// WHEN & THEN
			doThrow(new ConflictException(ErrorCode.PHONE_DUPLICATION))
				.when(memberValidator).validatePhoneNumberUniqueness(eq(newPhoneNumber));

			assertThatThrownBy(() -> profileUpdateService.updatePhoneNumber(memberId, phoneNumberUpdateRequest))
				.isInstanceOf(ConflictException.class)
				.extracting(exception -> ((ConflictException)exception).getErrorCode())
				.isEqualTo(ErrorCode.PHONE_DUPLICATION);

			assertThat(member.getPhoneNumber()).isNotEqualTo(newPhoneNumber);
			verify(memberCacheProcessor, never()).evictMemberCache(anyLong(), anyString());
		}
	}
}
