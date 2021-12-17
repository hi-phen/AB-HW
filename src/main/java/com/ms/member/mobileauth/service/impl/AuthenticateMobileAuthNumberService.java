package com.ms.member.mobileauth.service.impl;

import com.ms.member.mobileauth.client.MobileAuthApiClient;
import com.ms.member.mobileauth.service.AuthenticateMobileAuthNumberUseCase;
import com.ms.member.mobileauth.service.command.MobileAuthNumberAuthenticateCommand;
import com.ms.member.mobileauth.service.exception.InvalidAuthNumberException;
import com.ms.member.mobileauth.service.model.MobileAuthNumberAuthenticateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

/**
 * 전화번호 인증 번호를 통한 인증 수행 유즈케이스의 기본 구현 서비스.
 */
@Service
@RequiredArgsConstructor
public class AuthenticateMobileAuthNumberService implements AuthenticateMobileAuthNumberUseCase {

  private final MobileAuthApiClient mobileAuthApiClient;

  @Override
  public MobileAuthNumberAuthenticateResult authenticate(
      MobileAuthNumberAuthenticateCommand command)
      throws InvalidAuthNumberException {
    try {
      String token =
          mobileAuthApiClient.authenticate(command.getMobile(), command.getAuthNumber());
      return MobileAuthNumberAuthenticateResult.of(token);
    } catch (RestClientException e) {
      throw new InvalidAuthNumberException();
    }
  }
}
