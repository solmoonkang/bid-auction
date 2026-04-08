package com.bid.auction.auth.application.dto.session;

import java.io.Serializable;

import com.bid.auction.member.domain.model.Member;

public record SessionMember(
	Long id,

	String email,

	String nickname

) implements Serializable {

	public static SessionMember fromEntity(Member member) {
		return new SessionMember(
			member.getId(),
			member.getEmail(),
			member.getNickname()
		);
	}
}
