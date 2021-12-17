package com.ms.member.mobileauth.web;

import com.ms.member.mobileauth.service.AuthenticateMobileAuthNumberUseCase;
import com.ms.member.mobileauth.service.RequestMobileAuthUseCase;
import com.ms.member.mobileauth.service.command.MobileAuthNumberAuthenticateCommand;
import com.ms.member.mobileauth.service.command.MobileAuthRequestCommand;
import com.ms.member.mobileauth.service.exception.InvalidAuthNumberException;
import com.ms.member.mobileauth.web.domain.AuthenticateMobileAuthNumberRequest;
import com.ms.member.mobileauth.web.domain.RequestMobileAuthRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 전화번호 인증 컨트롤러.
 */
@RestController
@RequiredArgsConstructor
public class MobileAuthController {

  private final RequestMobileAuthUseCase requestMobileAuthUseCase;
  private final AuthenticateMobileAuthNumberUseCase authenticateMobileAuthNumberUseCase;

  /**
   * 전화번호 인증 요청.
   * 전화번호를 입력 받아 인증번호를 전송 한다.
   *
   * @param request 전화번호
   */
  @ResponseStatus(HttpStatus.OK)
  @PostMapping(value = "/mobile-auth/")
  void requestMobileAuth(@RequestBody @Valid RequestMobileAuthRequest request) {
    requestMobileAuthUseCase.request(MobileAuthRequestCommand.of(request.getMobile()));
  }

  /**
   * 전회번호 인증 토큰 요청.
   * 전화번호와 인증번호를 이용하여 인증 토큰을 요청한다.
   *
   * @param request 전화번호, 인증번호
   * @return 인증토큰
   */
  @GetMapping("/mobile-auth/token")
  ResponseEntity<?> getMobileAuthToken(
      @Valid AuthenticateMobileAuthNumberRequest request) {
    try {
      var result = authenticateMobileAuthNumberUseCase.authenticate(
          MobileAuthNumberAuthenticateCommand.of(request.getMobile(), request.getAuthNumber())
      );
      return ResponseEntity.ok(result);
    } catch (InvalidAuthNumberException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
