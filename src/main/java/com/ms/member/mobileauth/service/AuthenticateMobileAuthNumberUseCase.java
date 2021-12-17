package com.ms.member.mobileauth.service;

import com.ms.member.mobileauth.service.command.MobileAuthNumberAuthenticateCommand;
import com.ms.member.mobileauth.service.exception.InvalidAuthNumberException;
import com.ms.member.mobileauth.service.model.MobileAuthNumberAuthenticateResult;

/**
 * 전화번호 인증 번호를 통한 인증 수행 유즈케이스.
 * 입력 한 인증번호가 유효하면 토큰이 포함 된 결과를 리턴한다.
 *
 * @see RequestMobileAuthUseCase 전화번호 인증 요청
 */
public interface AuthenticateMobileAuthNumberUseCase {
  /**
   * 인증번호 인증.
   *
   * @param command 인증 요청 커맨드
   * @return 인증 결과
   * @throws InvalidAuthNumberException 잘못 된 인증번호 예외
   */
  MobileAuthNumberAuthenticateResult authenticate(MobileAuthNumberAuthenticateCommand command)
      throws InvalidAuthNumberException;
}
