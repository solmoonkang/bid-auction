package com.bid.auction.global.error.response;

import java.util.Map;

import com.bid.auction.global.error.model.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;

@Builder
public record ErrorResponse(
	String message,

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	Map<String, String> validation
) {

	public static ErrorResponse of(ErrorCode errorCode) {
		return new ErrorResponse(errorCode.getMessage(), null);
	}

	public static ErrorResponse of(ErrorCode errorCode, Map<String, String> validation) {
		return new ErrorResponse(errorCode.getMessage(), validation);
	}
}
