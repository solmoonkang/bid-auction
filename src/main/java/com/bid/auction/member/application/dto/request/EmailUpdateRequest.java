package com.bid.auction.member.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "EmailUpdateRequest", description = "이메일 수정 요청")
public record EmailUpdateRequest(

	@Schema(name = "이메일", example = "test@example.com")
	@NotBlank(message = "이메일을 입력해주세요.")
	@Email(message = "이메일 형식에 맞게 입력해주세요.")
	String email
) {
}
