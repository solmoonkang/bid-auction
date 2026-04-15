package com.bid.auction.global.error.exception;

import com.bid.auction.global.error.model.ErrorCode;

public final class BadRequestException extends BidAuctionException {

	public BadRequestException(ErrorCode errorCode) {
		super(errorCode);
	}
}
