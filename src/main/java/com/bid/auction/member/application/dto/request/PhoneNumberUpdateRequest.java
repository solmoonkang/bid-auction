package com.bid.auction.member.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Schema(name = "PhoneNumberUpdateRequest", description = "전화번호 수정 요청")
public record PhoneNumberUpdateRequest(

	@Schema(name = "전화번호", example = "01012345678")
	@NotBlank(message = "전화번호를 입력해주세요.")
	@Pattern(regexp = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", message = "전화번호 형식에 맞게 입력해주세요.")
	String phoneNumber
) {
}
