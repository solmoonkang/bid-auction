package com.bid.auction.authentication.presentation;

import static com.bid.auction.authentication.infrastructure.constant.SessionConst.*;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bid.auction.authentication.application.dto.request.LoginRequest;
import com.bid.auction.authentication.application.dto.session.SessionMember;
import com.bid.auction.authentication.application.service.LoginService;
import com.bid.auction.global.common.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "AUTHENTICATION", description = "인증 관리 API")
@RestController
@RequestMapping("/api/v1/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

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
}
