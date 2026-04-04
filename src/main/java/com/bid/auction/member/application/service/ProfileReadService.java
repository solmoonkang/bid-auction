package com.bid.auction.member.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bid.auction.member.application.component.MemberFinder;
import com.bid.auction.member.application.dto.response.ProfileResponse;
import com.bid.auction.member.application.mapper.MemberResponseMapper;
import com.bid.auction.member.domain.model.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileReadService {

	private final MemberFinder memberFinder;

	public ProfileResponse getProfile(Long memberId) {
		final Member member = memberFinder.findById(memberId);
		return MemberResponseMapper.toProfileResponse(member);
	}
}
