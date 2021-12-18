package com.ms.member.service.model;

import lombok.Value;

/**
 * 로그인 토큰.
 */
@Value(staticConstructor = "of")
public class LoginToken {
  String token;

  /**
   * 기본 생성자.
   *
   * @param token 로그인토큰
   */
  public LoginToken(String token) {
    this.token = token;
  }
}
