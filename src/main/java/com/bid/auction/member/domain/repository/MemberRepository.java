package com.bid.auction.member.domain.repository;

import java.util.Optional;

import com.bid.auction.member.domain.model.Member;

public interface MemberRepository {

	Member save(Member member);

	Optional<Member> findById(Long id);

	Optional<Member> findByEmail(String email);

	boolean existsByEmail(String email);

	boolean existsByNickname(String nickname);

	boolean existsByPhoneNumber(String phoneNumber);
}
