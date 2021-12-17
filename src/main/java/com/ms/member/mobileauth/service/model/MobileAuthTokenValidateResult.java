package com.ms.member.mobileauth.service.model;

import lombok.Value;

/**
 * 전화번호 인증 토큰 검증 결과.
 */
@Value(staticConstructor = "of")
public class MobileAuthTokenValidateResult {

  String mobile;

  /**
   * 기본 생성자.
   *
   * @param mobile 전화 번호.
   */
  public MobileAuthTokenValidateResult(String mobile) {
    this.mobile = mobile;
  }
}
