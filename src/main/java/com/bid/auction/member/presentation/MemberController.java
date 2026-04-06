package com.bid.auction.member.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bid.auction.authentication.application.dto.session.SessionMember;
import com.bid.auction.authentication.infrastructure.resolver.LoginMember;
import com.bid.auction.global.common.SuccessResponse;
import com.bid.auction.member.application.dto.request.EmailUpdateRequest;
import com.bid.auction.member.application.dto.request.NicknameUpdateRequest;
import com.bid.auction.member.application.dto.request.PhoneNumberUpdateRequest;
import com.bid.auction.member.application.dto.request.SignUpRequest;
import com.bid.auction.member.application.dto.response.ProfileResponse;
import com.bid.auction.member.application.service.ProfileReadService;
import com.bid.auction.member.application.service.ProfileUpdateService;
import com.bid.auction.member.application.service.SignUpService;
import com.bid.auction.member.application.service.WithdrawalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "MEMBER", description = "회원 관리 API")
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

	private final SignUpService signUpService;
	private final ProfileUpdateService profileUpdateService;
	private final ProfileReadService profileReadService;
	private final WithdrawalService withdrawalService;

	@Operation(summary = "회원가입", description = "신규 사용자를 등록합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "회원가입 성공"),
		@ApiResponse(responseCode = "400", description = "잘못된 형식의 입력값"),
		@ApiResponse(responseCode = "409", description = "이미 사용 중인 리소스와 충돌"),
		@ApiResponse(responseCode = "500", description = "서버 내부 오류")
	})
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/signup")
	public SuccessResponse<Void> signUp(
		@Valid @RequestBody SignUpRequest signUpRequest
	) {
		signUpService.signUp(signUpRequest);
		return SuccessResponse.ok();
	}

	@Operation(summary = "내 이메일 정보 수정", description = "현재 로그인한 회원의 이메일을 수정합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "이메일 수정 성공"),
		@ApiResponse(responseCode = "400", description = "잘못된 형식의 입력값"),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
		@ApiResponse(responseCode = "409", description = "이미 사용 중인 리소스와 충돌"),
		@ApiResponse(responseCode = "500", description = "서버 내부 오류")
	})
	@ResponseStatus(HttpStatus.OK)
	@PatchMapping("/me/email")
	public SuccessResponse<Void> updateEmail(
		@LoginMember SessionMember sessionMember,
		@Valid @RequestBody EmailUpdateRequest emailUpdateRequest
	) {
		profileUpdateService.updateEmail(sessionMember.id(), emailUpdateRequest);
		return SuccessResponse.ok();
	}

	@Operation(summary = "내 닉네임 정보 수정", description = "현재 로그인한 회원의 닉네임을 수정합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "닉네임 수정 성공"),
		@ApiResponse(responseCode = "400", description = "잘못된 형식의 입력값"),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
		@ApiResponse(responseCode = "409", description = "이미 사용 중인 리소스와 충돌"),
		@ApiResponse(responseCode = "500", description = "서버 내부 오류")
	})
	@ResponseStatus(HttpStatus.OK)
	@PatchMapping("/me/nickname")
	public SuccessResponse<Void> updateNickname(
		@LoginMember SessionMember sessionMember,
		@Valid @RequestBody NicknameUpdateRequest nicknameUpdateRequest
	) {
		profileUpdateService.updateNickname(sessionMember.id(), nicknameUpdateRequest);
		return SuccessResponse.ok();
	}

	@Operation(summary = "내 전화번호 정보 수정", description = "현재 로그인한 회원의 전화번호를 수정합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "전화번호 수정 성공"),
		@ApiResponse(responseCode = "400", description = "잘못된 형식의 입력값"),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
		@ApiResponse(responseCode = "409", description = "이미 사용 중인 리소스와 충돌"),
		@ApiResponse(responseCode = "500", description = "서버 내부 오류")
	})
	@ResponseStatus(HttpStatus.OK)
	@PatchMapping("/me/phone")
	public SuccessResponse<Void> updatePhoneNumber(
		@LoginMember SessionMember sessionMember,
		@Valid @RequestBody PhoneNumberUpdateRequest phoneNumberUpdateRequest
	) {
		profileUpdateService.updatePhoneNumber(sessionMember.id(), phoneNumberUpdateRequest);
		return SuccessResponse.ok();
	}

	@Operation(summary = "내 프로필 정보 조회", description = "현재 로그인한 회원의 프로필 정보를 조회합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "프로필 조회 성공"),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 사용자"),
		@ApiResponse(responseCode = "500", description = "서버 내부 오류")
	})
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/me")
	public SuccessResponse<ProfileResponse> getProfile(
		@LoginMember SessionMember sessionMember
	) {
		return SuccessResponse.ok(
			profileReadService.getProfile(sessionMember.id())
		);
	}

	@Operation(summary = "회원 탈퇴", description = "현재 로그인한 회원의 계정을 탈퇴 처리합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "회원 탈퇴 성공"),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 사용자"),
		@ApiResponse(responseCode = "500", description = "서버 내부 오류")
	})
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping
	public SuccessResponse<Void> withdraw(
		@LoginMember SessionMember sessionMember
	) {
		withdrawalService.withdraw(sessionMember.id());
		return SuccessResponse.ok();
	}
}
