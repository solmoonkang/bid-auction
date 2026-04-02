package com.bid.auction.member.domain.model;

import org.hibernate.annotations.Comment;

import com.bid.auction.global.common.BaseMappingEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(
	name = "MEMBERS",
	uniqueConstraints = {
		@UniqueConstraint(name = "uk_member_email", columnNames = "email"),
		@UniqueConstraint(name = "uk_member_nickname", columnNames = "nickname"),
		@UniqueConstraint(name = "uk_member_phone_number", columnNames = "phone_number")
	}
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseMappingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	@Comment("이메일")
	@Column(name = "email", nullable = false, length = 100)
	private String email;

	@Comment("비밀번호")
	@Column(name = "password", nullable = false)
	private String password;

	@Comment("이름")
	@Column(name = "name", nullable = false, length = 50)
	private String name;

	@Comment("닉네임")
	@Column(name = "nickname", nullable = false, length = 50)
	private String nickname;

	@Comment("전화번호")
	@Column(name = "phone_number", nullable = false, length = 20)
	private String phoneNumber;

	@Builder
	private Member(String email, String password, String name, String nickname, String phoneNumber) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.nickname = nickname;
		this.phoneNumber = phoneNumber;
	}

	public static Member signUp(String email, String password, String name, String nickname, String phoneNumber) {
		return Member.builder()
			.email(email)
			.password(password)
			.name(name)
			.nickname(nickname)
			.phoneNumber(phoneNumber)
			.build();
	}
}
