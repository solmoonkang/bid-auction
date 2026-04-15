package com.bid.auction.global.error.exception;

import com.bid.auction.global.error.model.ErrorCode;

import lombok.Getter;

@Getter
public abstract class BidAuctionException extends RuntimeException {

	private final ErrorCode errorCode;

	protected BidAuctionException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
