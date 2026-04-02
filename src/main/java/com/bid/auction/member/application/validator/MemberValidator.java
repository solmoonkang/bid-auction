package com.bid.auction.member.application.validator;

import org.springframework.stereotype.Component;

import com.bid.auction.global.error.exception.BadRequestException;
import com.bid.auction.global.error.exception.ConflictException;
import com.bid.auction.global.error.model.ErrorCode;
import com.bid.auction.member.domain.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberValidator {

	private final MemberRepository memberRepository;

	public void validateUniqueness(String email, String nickname, String phoneNumber) {
		memberRepository.findFirstByEmailOrNicknameOrPhoneNumber(email, nickname, phoneNumber)
			.ifPresent(member -> {
				validateEmailUniqueness(member.getEmail(), email);
				validateNicknameUniqueness(member.getNickname(), nickname);
				validatePhoneNumberUniqueness(member.getPhoneNumber(), phoneNumber);
			});
	}

	private void validateEmailUniqueness(String savedEmail, String email) {
		if (savedEmail.equals(email)) {
			throw new ConflictException(ErrorCode.EMAIL_DUPLICATION);
		}
	}

	private void validateNicknameUniqueness(String savedNickname, String nickname) {
		if (savedNickname.equals(nickname)) {
			throw new ConflictException(ErrorCode.NICKNAME_DUPLICATION);
		}
	}

	private void validatePhoneNumberUniqueness(String savedPhoneNumber, String phoneNumber) {
		if (savedPhoneNumber.equals(phoneNumber)) {
			throw new ConflictException(ErrorCode.PHONE_DUPLICATION);
		}
	}

	public void validatePasswordMatch(String password, String checkPassword) {
		if (!password.equals(checkPassword)) {
			throw new BadRequestException(ErrorCode.INVALID_INPUT_VALUE);
		}
	}
}
