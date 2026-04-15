package com.bid.auction.member.application.mapper;

import com.bid.auction.global.util.MaskingUtil;
import com.bid.auction.member.application.dto.response.ProfileResponse;
import com.bid.auction.member.domain.model.Member;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MemberResponseMapper {

	public static ProfileResponse toProfileResponse(Member member) {
		return new ProfileResponse(
			MaskingUtil.maskEmail(member.getEmail()),
			member.getName(),
			member.getNickname(),
			MaskingUtil.maskPhoneNumber(member.getPhoneNumber())
		);
	}
}
