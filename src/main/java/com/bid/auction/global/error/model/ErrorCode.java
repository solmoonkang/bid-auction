package com.bid.auction.global.error.model;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	// Common
	INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", "올바르지 않은 입력값입니다."),
	METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C002", "허용되지 않은 메서드입니다."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C003", "서버 내부 오류가 발생했습니다."),

	// Member
	EMAIL_DUPLICATION(HttpStatus.BAD_REQUEST, "M001", "이미 존재하는 이메일입니다."),
	NICKNAME_DUPLICATION(HttpStatus.BAD_REQUEST, "M002", "이미 존재하는 닉네임입니다."),
	PHONE_DUPLICATION(HttpStatus.BAD_REQUEST, "M003", "이미 존재하는 전화번호입니다."),
	LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "M004", "아이디 또는 비밀번호가 일치하지 않습니다."),
	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "M005", "회원을 찾을 수 없습니다.");

	private final HttpStatus status;
	private final String code;
	private final String message;
}
