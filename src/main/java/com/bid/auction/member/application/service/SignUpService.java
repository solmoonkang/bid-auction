package com.bid.auction.member.application.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bid.auction.member.application.dto.request.SignUpRequest;
import com.bid.auction.member.application.validator.MemberValidator;
import com.bid.auction.member.domain.model.Member;
import com.bid.auction.member.domain.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SignUpService {

	private final MemberRepository memberRepository;

	private final MemberValidator memberValidator;

	private final PasswordEncoder passwordEncoder;

	public void signUp(SignUpRequest signUpRequest) {
		memberValidator.validatePasswordConfirmation(
			signUpRequest.password(),
			signUpRequest.checkPassword()
		);
		memberValidator.validateUniqueness(
			signUpRequest.email(),
			signUpRequest.nickname(),
			signUpRequest.phoneNumber()
		);

		final String encodedPassword = passwordEncoder.encode(signUpRequest.password());

		final Member member = Member.signUp(
			signUpRequest.email(),
			encodedPassword,
			signUpRequest.name(),
			signUpRequest.nickname(),
			signUpRequest.phoneNumber()
		);

		memberRepository.save(member);
	}
}
