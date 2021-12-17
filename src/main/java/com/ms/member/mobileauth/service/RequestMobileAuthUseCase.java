package com.ms.member.mobileauth.service;

import com.ms.member.mobileauth.service.command.MobileAuthRequestCommand;

/**
 * 전화번호 인증 요청 유즈케이스.
 */
public interface RequestMobileAuthUseCase {
  /**
   * 인증 요청.
   *
   * @param command 인증 요청 커맨드.
   */
  void request(MobileAuthRequestCommand command);
}
