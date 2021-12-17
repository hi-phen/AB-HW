package com.ms.member.mobileauth.service.model;

import lombok.Value;

/**
 * 전화번호 인증 결과.
 */
@Value(staticConstructor = "of")
public class MobileAuthNumberAuthenticateResult {

  String token;

  /**
   * 기본 생성자.
   *
   * @param token 인증 토큰
   */
  private MobileAuthNumberAuthenticateResult(String token) {
    this.token = token;
  }
}
