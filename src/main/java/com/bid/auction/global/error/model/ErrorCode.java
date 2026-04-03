package com.bid.auction.global.error.model;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	// COMMON
	INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "올바르지 않은 입력값입니다."),
	METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 메서드입니다."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."),

	LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED, "로그인이 필요한 서비스입니다. 인증 정보를 확인해주세요."),
	SESSION_EXPIRED(HttpStatus.UNAUTHORIZED, "세션이 만료되었습니다. 다시 로그인해주세요."),

	// MEMBER
	EMAIL_DUPLICATION(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다."),
	NICKNAME_DUPLICATION(HttpStatus.BAD_REQUEST, "이미 존재하는 닉네임입니다."),
	PHONE_DUPLICATION(HttpStatus.BAD_REQUEST, "이미 존재하는 전화번호입니다."),
	LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 일치하지 않습니다."),
	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다.");

	private final HttpStatus status;
	private final String message;
}
