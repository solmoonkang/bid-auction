package com.bid.auction.member.infrastructure.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WithdrawalConst {

	public static final String WITHDRAWN_NAME = "탈퇴회원";
	public static final String WITHDRAWN_PASSWORD = "WITHDRAWN_MEMBER";

	public static final String WITHDRAWN_SUFFIX_FORMAT = "%s_WITHDRAWN_%s";
	public static final int UUID_SHORT_LENGTH = 8;
}
