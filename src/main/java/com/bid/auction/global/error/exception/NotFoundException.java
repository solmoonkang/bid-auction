package com.bid.auction.global.error.exception;

import com.bid.auction.global.error.model.ErrorCode;

public final class NotFoundException extends BidAuctionException {

	public NotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
