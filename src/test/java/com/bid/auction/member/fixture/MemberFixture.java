package com.bid.auction.member.fixture;

import com.bid.auction.member.application.dto.request.EmailUpdateRequest;
import com.bid.auction.member.application.dto.request.NicknameUpdateRequest;
import com.bid.auction.member.application.dto.request.PhoneNumberUpdateRequest;
import com.bid.auction.member.application.dto.request.SignUpRequest;
import com.bid.auction.member.application.dto.request.WithdrawalRequest;
import com.bid.auction.member.application.dto.response.ProfileResponse;
import com.bid.auction.member.domain.model.Member;

public class MemberFixture {

	public static Member aMember() {
		return Member.signUp(
			"test@example.com",
			"encodedPassword123!",
			"testName",
			"testNickname",
			"01023456789"
		);
	}

	public static Member aMemberWithEmail(String email) {
		return Member.signUp(
			email,
			"encodedPassword123!",
			"testName",
			"testNickname",
			"01023456789"
		);
	}

	public static Member aMemberWithNickname(String nickname) {
		return Member.signUp(
			"test@example.com",
			"encodedPassword123!",
			"testName",
			nickname,
			"01023456789"
		);
	}

	public static Member aMemberWithPhoneNumber(String phoneNumber) {
		return Member.signUp(
			"test@example.com",
			"encodedPassword123!",
			"testName",
			"testNickname",
			phoneNumber
		);
	}

	public static SignUpRequest aSignUpRequest() {
		return new SignUpRequest(
			"test@example.com",
			"password123!",
			"password123!",
			"testName",
			"testNickname",
			"01023456789"
		);
	}

	public static SignUpRequest aSignUpRequestWithEmail(String email) {
		return new SignUpRequest(
			email,
			"password123!",
			"password123!",
			"testName",
			"testNickname",
			"01023456789"
		);
	}

	public static SignUpRequest aSignUpRequestWithNickname(String nickname) {
		return new SignUpRequest(
			"test@example.com",
			"password123!",
			"password123!",
			"testName",
			nickname,
			"01023456789"
		);
	}

	public static SignUpRequest aSignUpRequestWithPhoneNumber(String phoneNumber) {
		return new SignUpRequest(
			"test@example.com",
			"password123!",
			"password123!",
			"testName",
			"testNickname",
			phoneNumber
		);
	}

	public static SignUpRequest aSignUpRequestWithPasswordMismatch() {
		return new SignUpRequest(
			"test@example.com",
			"password123!",
			"wrongPassword123!",
			"testName",
			"testNickname",
			"01023456789"
		);
	}

	public static EmailUpdateRequest aEmailUpdateRequest(String email) {
		return new EmailUpdateRequest(
			email
		);
	}

	public static NicknameUpdateRequest aNicknameUpdateRequest(String nickname) {
		return new NicknameUpdateRequest(
			nickname
		);
	}

	public static PhoneNumberUpdateRequest aPhoneNumberUpdateRequest(String phoneNumber) {
		return new PhoneNumberUpdateRequest(
			phoneNumber
		);
	}

	public static ProfileResponse aProfileResponse(Member member) {
		return new ProfileResponse(
			member.getEmail(),
			member.getName(),
			member.getNickname(),
			member.getPhoneNumber()
		);
	}

	public static WithdrawalRequest aWithdrawalRequest() {
		return new WithdrawalRequest(
			"password123!"
		);
	}
}
