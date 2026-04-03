package com.bid.auction.member.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "NicknameUpdateRequest", description = "닉네임 수정 요청")
public record NicknameUpdateRequest(

	@Schema(name = "닉네임", example = "test@example.com")
	@NotBlank(message = "닉네임을 입력해주세요.")
	String nickname
) {
}
