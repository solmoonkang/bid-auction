package com.bid.auction.auth.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bid.auction.auth.application.dto.request.LoginRequest;
import com.bid.auction.auth.application.dto.session.SessionMember;
import com.bid.auction.global.error.model.ErrorCode;
import com.bid.auction.member.application.component.MemberFinder;
import com.bid.auction.member.application.validator.MemberValidator;
import com.bid.auction.member.domain.model.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {

	private final MemberFinder memberFinder;

	private final MemberValidator memberValidator;

	public SessionMember login(LoginRequest loginRequest) {
		final Member member = memberFinder.findByEmail(loginRequest.email());

		memberValidator.validatePassword(loginRequest.password(), member.getPassword(), ErrorCode.LOGIN_FAILED);

		return SessionMember.fromEntity(member);
	}
}
