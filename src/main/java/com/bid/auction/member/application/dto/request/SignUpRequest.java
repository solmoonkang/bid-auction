package com.bid.auction.member.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Schema(name = "SignUpRequest", description = "회원가입 요청")
public record SignUpRequest(

	@Schema(name = "이메일", example = "test@example.com")
	@NotBlank(message = "이메일을 입력해주세요.")
	@Email(message = "이메일 형식에 맞게 입력해주세요.")
	String email,

	@Schema(name = "비밀번호", example = "password@1")
	@NotBlank(message = "비밀번호를 입력해주세요.")
	@Pattern(regexp = "^[A-Za-z]{8,20}$", message = "비밀번호는 영문자 8자 이상 20자 이하로 입력해주세요.")
	String password,

	@Schema(name = "확인 비밀번호", example = "password@1")
	@NotBlank(message = "확인 비밀번호 입력해주세요.")
	@Pattern(regexp = "^[A-Za-z]{8,20}$", message = "비밀번호는 영문자 8자 이상 20자 이하로 입력해주세요.")
	String checkPassword,

	@Schema(name = "이름", example = "홍길동")
	@NotBlank(message = "이름을 입력해주세요.")
	String name,

	@Schema(name = "닉네임", example = "흥글등")
	@NotBlank(message = "닉네임을 입력해주세요.")
	String nickname,

	@Schema(name = "전화번호", example = "01012345678")
	@NotBlank(message = "전화번호를 입력해주세요.")
	@Pattern(regexp = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", message = "전화번호 형식에 맞게 입력해주세요.")
	String phoneNumber
) {
}
