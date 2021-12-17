package com.ms.member.mobileauth.service;

import com.ms.member.mobileauth.service.command.ValidateMobileAuthTokenCommand;
import com.ms.member.mobileauth.service.exception.InvalidMobileAuthTokenException;
import com.ms.member.mobileauth.service.model.MobileAuthTokenValidateResult;

/**
 * 전화번호 인증 토큰 검증 유즈케이스.
 */
public interface ValidateMobileAuthTokenUseCase {

  /**
   * 토큰 검증.
   *
   * @param command 토큰 검증 요청 커맨드
   * @return 토큰 검증 결과
   * @throws InvalidMobileAuthTokenException 잘못 된 인증토큰 예외
   */
  MobileAuthTokenValidateResult validate(ValidateMobileAuthTokenCommand command)
      throws InvalidMobileAuthTokenException;
}
