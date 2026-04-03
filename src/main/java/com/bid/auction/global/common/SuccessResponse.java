package com.bid.auction.global.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;

public record SuccessResponse<T>(

	@Schema(description = "성공 여부")
	boolean isSuccess,

	@Schema(description = "응답 데이터")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	T data
) {

	public static <T> SuccessResponse<T> ok() {
		return new SuccessResponse<>(true, null);
	}

	public static <T> SuccessResponse<T> ok(T data) {
		return new SuccessResponse<>(true, data);
	}
}
