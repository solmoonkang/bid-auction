package com.bid.auction.member.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Schema(name = "WithdrawalRequest", description = "회원 탈퇴 요청")
public record WithdrawalRequest(

	@Schema(name = "비밀번호", example = "password@1")
	@NotBlank(message = "비밀번호를 입력해주세요.")
	@Pattern(regexp = "^[A-Za-z]{8,20}$", message = "비밀번호는 영문자 8자 이상 20자 이하로 입력해주세요.")
	String password
) {
}
