package com.bid.auction.authentication.infrastructure.interceptor;

import static com.bid.auction.authentication.infrastructure.constant.SessionConst.*;

import org.springframework.web.servlet.HandlerInterceptor;

import com.bid.auction.global.error.exception.UnauthorizedException;
import com.bid.auction.global.error.model.ErrorCode;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		Object handler
	) {
		final String requestURI = httpServletRequest.getRequestURI();
		log.info("인증 체크 인터셉터 실행: {}", requestURI);

		final HttpSession httpSession = httpServletRequest.getSession(false);

		if (httpSession == null || httpSession.getAttribute(MEMBER_SESSION) == null) {
			log.warn("미인증 사용자 요청: {}", requestURI);
			throw new UnauthorizedException(ErrorCode.LOGIN_REQUIRED);
		}

		return true;
	}
}
