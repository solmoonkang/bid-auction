package com.bid.auction.global.error.exception;

import com.bid.auction.global.error.model.ErrorCode;

public final class UnauthorizedException extends BidAuctionException {

	public UnauthorizedException(ErrorCode errorCode) {
		super(errorCode);
	}
}
