package com.bid.auction.member.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bid.auction.member.domain.model.Member;
import com.bid.auction.member.domain.repository.MemberRepository;

public interface MemberJpaRepository extends JpaRepository<Member, Long>, MemberRepository {
}
