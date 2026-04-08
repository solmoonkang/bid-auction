package com.bid.auction.member.fixture;

import com.bid.auction.member.application.dto.request.SignUpRequest;
import com.bid.auction.member.domain.model.Member;

public class MemberFixture {

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

	public static Member aMember() {
		return Member.signUp(
			"test@example.com",
			"encodedPassword123!",
			"testName",
			"testNickname",
			"01023456789"
		);
	}
}
