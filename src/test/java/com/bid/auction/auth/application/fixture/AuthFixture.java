package com.bid.auction.auth.application.fixture;

import com.bid.auction.auth.application.dto.request.LoginRequest;

public class AuthFixture {

	public static LoginRequest aLoginRequest() {
		return new LoginRequest(
			"test@example.com",
			"password123!"
		);
	}

	public static LoginRequest aLoginRequestWithEmail(String email) {
		return new LoginRequest(
			email,
			"password123!"
		);
	}

	public static LoginRequest aLoginRequestWithWrongPassword() {
		return new LoginRequest(
			"test@example.com",
			"wrongPassword123!"
		);
	}
}
