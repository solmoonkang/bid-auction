package com.bid.auction.auth.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Schema(name = "LoginRequest", description = "로그인 요청")
public record LoginRequest(

	@Schema(name = "이메일", example = "test@example.com")
	@NotBlank(message = "이메일을 입력해주세요.")
	@Email(message = "이메일 형식에 맞게 입력해주세요.")
	String email,

	@Schema(name = "비밀번호", example = "password@1")
	@NotBlank(message = "비밀번호를 입력해주세요.")
	@Pattern(regexp = "^[A-Za-z]{8,20}$", message = "비밀번호는 영문자 8자 이상 20자 이하로 입력해주세요.")
	String password
) {
}
