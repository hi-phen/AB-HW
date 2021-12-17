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

@RestController
@RequiredArgsConstructor
public class MobileAuthController {

  private final RequestMobileAuthUseCase requestMobileAuthUseCase;
  private final AuthenticateMobileAuthNumberUseCase authenticateMobileAuthNumberUseCase;

  @ResponseStatus(HttpStatus.OK)
  @PostMapping(value = "/mobile-auth/")
  void requestMobileAuth(@RequestBody @Valid RequestMobileAuthRequest request) {
    requestMobileAuthUseCase.request(MobileAuthRequestCommand.of(request.getMobile()));
  }

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
