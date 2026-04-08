package com.bid.auction.auth.presentation;

import static com.bid.auction.auth.infrastructure.constant.SessionConst.*;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bid.auction.auth.application.dto.request.LoginRequest;
import com.bid.auction.auth.application.dto.session.SessionMember;
import com.bid.auction.auth.application.service.LoginService;
import com.bid.auction.global.common.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "AUTHENTICATION", description = "인증 관리 API")
@RestController
@RequestMapping("/api/v1/authentication")
@RequiredArgsConstructor
public class AuthController {

	private final LoginService loginService;

	@Operation(summary = "로그인", description = "세션 로그인을 수행합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "로그인 성공"),
		@ApiResponse(responseCode = "401", description = "인증 실패")
	})
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/login")
	public SuccessResponse<Void> login(
		@Valid @RequestBody LoginRequest loginRequest,
		@Parameter(hidden = true) HttpServletRequest httpServletRequest
	) {
		final SessionMember sessionMember = loginService.login(loginRequest);
		httpServletRequest.getSession().setAttribute(MEMBER_SESSION, sessionMember);
		return SuccessResponse.ok();
	}

	@Operation(summary = "로그아웃", description = "현재 세션을 무효화하여 로그아웃을 수행합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "로그아웃 성공")
	})
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/logout")
	public SuccessResponse<Void> logout(
		@Parameter(hidden = true) HttpServletRequest httpServletRequest
	) {
		Optional.ofNullable(httpServletRequest.getSession(false))
			.ifPresent(HttpSession::invalidate);
		return SuccessResponse.ok();
	}
}
