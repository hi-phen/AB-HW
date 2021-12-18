package com.ms.member.web;

import com.ms.member.exception.DuplicateValueException;
import com.ms.member.exception.MemberNotFoundException;
import com.ms.member.exception.UnAuthorizedException;
import com.ms.member.mobileauth.service.ValidateMobileAuthTokenUseCase;
import com.ms.member.mobileauth.service.command.ValidateMobileAuthTokenCommand;
import com.ms.member.mobileauth.service.exception.InvalidMobileAuthTokenException;
import com.ms.member.service.MemberCreateUseCase;
import com.ms.member.service.MemberFindUseCase;
import com.ms.member.service.command.MemberLoginCommand;
import com.ms.member.service.command.MemberPersistCommand;
import com.ms.member.service.impl.LoginServiceFactory;
import com.ms.member.web.model.LoginRequest;
import com.ms.member.web.model.MemberCreateRequest;
import com.ms.member.web.model.MemberInfoResponse;
import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 회원 컨트롤러.
 */
@RestController
@RequiredArgsConstructor
public class MemberController {

  private final MemberCreateUseCase memberCreateUseCase;
  private final ValidateMobileAuthTokenUseCase validateMobileAuthTokenUseCase;
  private final LoginServiceFactory loginServiceFactory;
  private final MemberFindUseCase memberFindUseCase;

  /**
   * 회원 가입(생성).
   *
   * @param request 회원 정보
   * @return 성공시 201, 검증실패시 422
   */
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/member")
  ResponseEntity<?> create(@RequestBody @Valid MemberCreateRequest request) {
    try {
      validateMobileAuthToken(request);

      var member = memberCreateUseCase.create(MemberPersistCommand.of()
          .email(request.getEmail())
          .mobile(request.getMobile())
          .name(request.getName())
          .nickName(request.getNickName())
          .password(request.getPassword())
          .build()
      );

      return ResponseEntity
          .created(URI.create(String.format("/member/%s", member.getId())))
          .build();
    } catch (InvalidMobileAuthTokenException | DuplicateValueException e) {
      return ResponseEntity.unprocessableEntity().body(e.getMessage());
    }
  }

  private void validateMobileAuthToken(MemberCreateRequest request)
      throws InvalidMobileAuthTokenException {
    validateMobileAuthTokenUseCase.validate(
        ValidateMobileAuthTokenCommand.of(request.getMobileAuthToken()));
  }

  /**
   * 회원 로그인.
   *
   * @param request 로그인 정보
   * @return 로그인 토큰 또는 실패시 401 응답
   */
  @PostMapping("/member/login")
  ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
    try {
      return ResponseEntity.ok(
          loginServiceFactory.getLoginService(request.getLoginKeyType())
              .login(MemberLoginCommand.of(request.getKey(), request.getPassword()))
      );
    } catch (MemberNotFoundException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }

  /**
   * 회원 조회 (내정보 조회).
   *
   * @param id    회원 아이디
   * @param token 로그인 토큰
   * @return 회원 정보
   */
  @GetMapping("/member/{id}")
  ResponseEntity<?> getMember(@PathVariable Long id,
                              @RequestHeader("authorization") String token) {
    try {
      validateLoginToken(id, token);
      var member = memberFindUseCase.findById(id);
      return ResponseEntity.ok(
          MemberInfoResponse.of()
              .email(member.getEmail())
              .id(member.getId())
              .mobile(member.getMobile())
              .name(member.getName())
              .nickName(member.getNickName())
              .build()
      );
    } catch (UnAuthorizedException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    } catch (MemberNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  private void validateLoginToken(Long id, String token) throws UnAuthorizedException {
    if (!token.equals(String.valueOf(id))) {
      throw new UnAuthorizedException();
    }
  }
}
