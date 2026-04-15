package com.bid.auction.global.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MaskingUtil {

	private static final String EMAIL_DOMAIN_SEPARATOR = "@";
	private static final String MASKING_CHARACTER = "*";
	private static final int MAX_MASKING_COUNT = 3;

	private static final String PHONE_NUMBER_SEPARATOR = "-";
	private static final int PHONE_NUMBER_LENGTH = 11;

	public static String maskEmail(String email) {
		final int separatorIndex = email.indexOf(EMAIL_DOMAIN_SEPARATOR);
		final String id = email.substring(0, separatorIndex);
		final String domain = email.substring(separatorIndex);

		if (id.length() <= 1) return MASKING_CHARACTER + domain;
		if (id.length() == 2) return id.charAt(0) + MASKING_CHARACTER + domain;

		final int maskingCount = Math.min(id.length() - 2, MAX_MASKING_COUNT);

		return id.charAt(0)
			   + MASKING_CHARACTER.repeat(maskingCount)
			   + id.charAt(id.length() - 1)
			   + domain;
	}

	public static String maskPhoneNumber(String phoneNumber) {
		if (phoneNumber.length() == PHONE_NUMBER_LENGTH)
			return phoneNumber.substring(0, 3)
				   + PHONE_NUMBER_SEPARATOR
				   + MASKING_CHARACTER.repeat(4)
				   + PHONE_NUMBER_SEPARATOR
				   + phoneNumber.substring(7);

		return phoneNumber;
	}
}
