package com.bid.auction.member.application.component;

import java.util.Objects;

import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberCacheProcessor {

	private static final String ID_CACHE_VALUE = "ID";
	private static final String EMAIL_CACHE_VALUE = "EMAIL";

	private final CacheManager cacheManager;

	public void evictMemberCache(Long id, String email) {
		Objects.requireNonNull(cacheManager.getCache(ID_CACHE_VALUE)).evict(id);
		Objects.requireNonNull(cacheManager.getCache(EMAIL_CACHE_VALUE)).evict(email);
	}
}
