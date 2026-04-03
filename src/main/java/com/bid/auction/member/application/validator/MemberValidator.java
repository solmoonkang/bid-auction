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
				if (member.getEmail().equals(email))
					throw new ConflictException(ErrorCode.EMAIL_DUPLICATION);
				if (member.getNickname().equals(nickname))
					throw new ConflictException(ErrorCode.NICKNAME_DUPLICATION);
				if (member.getPhoneNumber().equals(phoneNumber))
					throw new ConflictException(ErrorCode.PHONE_DUPLICATION);
			});
	}

	public void validateEmailUniqueness(String email) {
		if (memberRepository.existsByEmail(email)) {
			throw new ConflictException(ErrorCode.EMAIL_DUPLICATION);
		}
	}

	public void validateNicknameUniqueness(String nickname) {
		if (memberRepository.existsByNickname(nickname)) {
			throw new ConflictException(ErrorCode.NICKNAME_DUPLICATION);
		}
	}

	public void validatePhoneNumberUniqueness(String phoneNumber) {
		if (memberRepository.existsByPhoneNumber(phoneNumber)) {
			throw new ConflictException(ErrorCode.PHONE_DUPLICATION);
		}
	}

	public void validatePasswordMatch(String password, String checkPassword) {
		if (!password.equals(checkPassword)) {
			throw new BadRequestException(ErrorCode.INVALID_INPUT_VALUE);
		}
	}
}
