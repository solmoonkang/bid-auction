package com.bid.auction.member.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ProfileResponse", description = "사용자 프로필 정보 응답")
public record ProfileResponse(

	@Schema(description = "마스킹 처리된 이메일", example = "t**t@example.com")
	String email,

	@Schema(description = "사용자 이름", example = "홍길동")
	String name,

	@Schema(description = "사용자 닉네임", example = "흥글등")
	String nickname,

	@Schema(description = "포맷팅 및 마스킹 처리된 전화번호", example = "010-xxxx-6789")
	String phoneNumber
) {
}
