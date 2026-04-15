package com.bid.auction.member.domain.model;

import static com.bid.auction.member.infrastructure.constant.WithdrawalConst.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLRestriction;

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
@SQLRestriction("withdrawn_at IS NULL")
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

	@Comment("탈퇴 일시")
	@Column(name = "withdrawn_at")
	private LocalDateTime withdrawnAt;

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

	public boolean isSameEmail(String email) {
		return this.email.equals(email);
	}

	public boolean isSameNickname(String nickname) {
		return this.nickname.equals(nickname);
	}

	public boolean isSamePhoneNumber(String phoneNumber) {
		return this.phoneNumber.equals(phoneNumber);
	}

	public void updateEmail(String email) {
		this.email = email;
	}

	public void updateNickname(String nickname) {
		this.nickname = nickname;
	}

	public void updatePhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void withdraw(LocalDateTime withdrawnAt) {
		if (isWithdrawn()) return;

		this.withdrawnAt = withdrawnAt;

		this.email = formatWithdrawn(this.email);
		this.nickname = formatWithdrawn(this.nickname);
		this.phoneNumber = formatWithdrawn(this.phoneNumber);

		this.name = WITHDRAWN_NAME;
		this.password = WITHDRAWN_PASSWORD;
	}

	public boolean isWithdrawn() {
		return withdrawnAt != null;
	}

	private String formatWithdrawn(String source) {
		return String.format(
			WITHDRAWN_SUFFIX_FORMAT,
			source,
			UUID.randomUUID().toString().substring(0, UUID_SHORT_LENGTH)
		);
	}
}
