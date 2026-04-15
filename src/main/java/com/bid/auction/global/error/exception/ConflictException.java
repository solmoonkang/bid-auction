package com.bid.auction.global.error.exception;

import com.bid.auction.global.error.model.ErrorCode;

public final class ConflictException extends BidAuctionException {

	public ConflictException(ErrorCode errorCode) {
		super(errorCode);
	}
}
