package com.bid.auction.global.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bid.auction.authentication.infrastructure.interceptor.LoginCheckInterceptor;
import com.bid.auction.authentication.infrastructure.resolver.LoginMemberArgumentResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	private static final String[] BUSINESS_WHITELIST = {
		"/api/v1/members/signup", "/api/v1/authentication/login", "/api/v1/authentication/logout"
	};
	private static final String[] INFRASTRUCTURE_WHITELIST = {
		"/swagger-ui/**", "/v3/api-docs/**", "/error", "/favicon.ico"
	};

	@Override
	public void addInterceptors(InterceptorRegistry interceptorRegistry) {
		interceptorRegistry.addInterceptor(new LoginCheckInterceptor())
			.order(1)
			.addPathPatterns("/**")
			.excludePathPatterns(BUSINESS_WHITELIST)
			.excludePathPatterns(INFRASTRUCTURE_WHITELIST);
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new LoginMemberArgumentResolver());
	}
}
