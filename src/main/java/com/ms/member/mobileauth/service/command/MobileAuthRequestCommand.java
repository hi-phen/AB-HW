package com.ms.member.mobileauth.service.command;

import lombok.Value;

/**
 * 전화번호 인증 요청 커맨드.
 */
@Value(staticConstructor = "of")
public class MobileAuthRequestCommand {
  String mobile;

  /**
   * 기본 생성자.
   *
   * @param mobile 전화번호
   */
  private MobileAuthRequestCommand(String mobile) {
    this.mobile = mobile;
  }
}
