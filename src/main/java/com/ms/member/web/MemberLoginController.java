package com.ms.member.web;

import com.ms.member.exception.MemberNotFoundException;
import com.ms.member.service.command.MemberLoginCommand;
import com.ms.member.service.impl.LoginServiceFactory;
import com.ms.member.web.model.LoginRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 회원 로그인 컨트롤러.
 */
@RestController
@RequiredArgsConstructor
public class MemberLoginController {

  private final LoginServiceFactory loginServiceFactory;

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
}
