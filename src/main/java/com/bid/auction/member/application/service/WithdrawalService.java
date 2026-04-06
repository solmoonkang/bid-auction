package com.bid.auction.member.application.service;

import java.time.Clock;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bid.auction.member.application.component.MemberCacheProcessor;
import com.bid.auction.member.application.component.MemberFinder;
import com.bid.auction.member.domain.model.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class WithdrawalService {

	private final MemberFinder memberFinder;

	private final MemberCacheProcessor memberCacheProcessor;
	private final Clock clock;

	public void withdraw(Long memberId) {
		final Member member = memberFinder.findById(memberId);
		final String previousEmail = member.getEmail();

		member.withdraw(LocalDateTime.now(clock));

		memberCacheProcessor.evictMemberCache(memberId, previousEmail);
		memberCacheProcessor.evictMemberCache(memberId, member.getEmail());
	}
}
