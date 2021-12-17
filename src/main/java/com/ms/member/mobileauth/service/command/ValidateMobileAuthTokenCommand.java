package com.ms.member.mobileauth.service.command;

import lombok.Value;

/**
 * 전화번호 인증 토큰 검증 커맨드.
 */
@Value(staticConstructor = "of")
public class ValidateMobileAuthTokenCommand {
  String token;

  /**
   * 기본 생성자.
   *
   * @param token 인증 토큰.
   */
  private ValidateMobileAuthTokenCommand(String token) {
    this.token = token;
  }
}
