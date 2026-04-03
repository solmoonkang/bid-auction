package com.bid.auction.authentication.infrastructure.resolver;

import static com.bid.auction.authentication.infrastructure.constant.SessionConst.*;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.bid.auction.authentication.application.dto.session.SessionMember;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter methodParameter) {
		boolean hasLoginMemberAnnotation = methodParameter.hasMethodAnnotation(LoginMember.class);
		boolean isSessionMemberType = SessionMember.class.isAssignableFrom(methodParameter.getParameterType());
		return hasLoginMemberAnnotation && isSessionMemberType;
	}

	@Override
	public Object resolveArgument(
		MethodParameter methodParameter,
		ModelAndViewContainer mavContainer,
		NativeWebRequest nativeWebRequest,
		WebDataBinderFactory webDataBinderFactory
	) {
		HttpServletRequest httpServletRequest = (HttpServletRequest)nativeWebRequest.getNativeRequest();
		HttpSession httpSession = httpServletRequest.getSession(false);

		if (httpSession == null)
			return null;

		return httpSession.getAttribute(MEMBER_SESSION);
	}
}
