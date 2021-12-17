package com.ms.member.mobileauth.service.command;

import lombok.Value;

/**
 * 전화번호 인증의 인증번호 검증 커맨드.
 */
@Value(staticConstructor = "of")
public class MobileAuthNumberAuthenticateCommand {
  String mobile;
  String authNumber;

  /**
   * 기본 생성자.
   *
   * @param mobile     전화번호
   * @param authNumber 인증번호
   */
  private MobileAuthNumberAuthenticateCommand(String mobile, String authNumber) {
    this.mobile = mobile;
    this.authNumber = authNumber;
  }
}
