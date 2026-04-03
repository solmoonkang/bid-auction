package com.bid.auction.member.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bid.auction.member.application.component.MemberFinder;
import com.bid.auction.member.application.dto.request.EmailUpdateRequest;
import com.bid.auction.member.application.dto.request.NicknameUpdateRequest;
import com.bid.auction.member.application.dto.request.PhoneNumberUpdateRequest;
import com.bid.auction.member.application.validator.MemberValidator;
import com.bid.auction.member.domain.model.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileUpdateService {

	private final MemberFinder memberFinder;

	private final MemberValidator memberValidator;

	public void updateEmail(Long memberId, EmailUpdateRequest emailUpdateRequest) {
		final Member member = memberFinder.findById(memberId);

		if (member.isSameEmail(emailUpdateRequest.email()))
			return;

		memberValidator.validateEmailUniqueness(emailUpdateRequest.email());
		member.updateEmail(emailUpdateRequest.email());
	}

	public void updateNickname(Long memberId, NicknameUpdateRequest nicknameUpdateRequest) {
		final Member member = memberFinder.findById(memberId);

		if (member.isSameNickname(nicknameUpdateRequest.nickname()))
			return;

		memberValidator.validateNicknameUniqueness(nicknameUpdateRequest.nickname());
		member.updateNickname(nicknameUpdateRequest.nickname());
	}

	public void updatePhoneNumber(Long memberId, PhoneNumberUpdateRequest phoneNumberUpdateRequest) {
		final Member member = memberFinder.findById(memberId);

		if (member.isSamePhoneNumber(phoneNumberUpdateRequest.phoneNumber()))
			return;

		memberValidator.validatePhoneNumberUniqueness(phoneNumberUpdateRequest.phoneNumber());
		member.updatePhoneNumber(phoneNumberUpdateRequest.phoneNumber());
	}
}
