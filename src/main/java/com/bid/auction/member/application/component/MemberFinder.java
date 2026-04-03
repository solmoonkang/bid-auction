package com.bid.auction.member.application.component;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bid.auction.global.error.exception.BadRequestException;
import com.bid.auction.global.error.exception.NotFoundException;
import com.bid.auction.global.error.model.ErrorCode;
import com.bid.auction.member.domain.model.Member;
import com.bid.auction.member.domain.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberFinder {

	private final MemberRepository memberRepository;

	public Member findByEmail(String email) {
		return memberRepository.findByEmail(email)
			.orElseThrow(() -> new BadRequestException(ErrorCode.LOGIN_FAILED));
	}

	public Member findById(Long id) {
		return memberRepository.findById(id)
			.orElseThrow(() -> new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));
	}
}
