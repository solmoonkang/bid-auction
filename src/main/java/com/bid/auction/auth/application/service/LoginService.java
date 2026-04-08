package com.bid.auction.auth.application.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bid.auction.auth.application.dto.request.LoginRequest;
import com.bid.auction.auth.application.dto.session.SessionMember;
import com.bid.auction.global.error.exception.BadRequestException;
import com.bid.auction.global.error.model.ErrorCode;
import com.bid.auction.member.application.component.MemberFinder;
import com.bid.auction.member.domain.model.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {

	private final MemberFinder memberFinder;

	private final PasswordEncoder passwordEncoder;

	public SessionMember login(LoginRequest loginRequest) {
		final Member member = memberFinder.findByEmail(loginRequest.email());

		validatePassword(member.getPassword(), loginRequest.password());

		return SessionMember.fromEntity(member);
	}

	private void validatePassword(String encodedPassword, String rawPassword) {
		if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
			throw new BadRequestException(ErrorCode.LOGIN_FAILED);
		}
	}
}
