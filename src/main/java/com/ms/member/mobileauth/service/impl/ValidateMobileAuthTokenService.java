package com.ms.member.mobileauth.service.impl;

import com.ms.member.mobileauth.client.MobileAuthApiClient;
import com.ms.member.mobileauth.service.ValidateMobileAuthTokenUseCase;
import com.ms.member.mobileauth.service.command.ValidateMobileAuthTokenCommand;
import com.ms.member.mobileauth.service.exception.InvalidMobileAuthTokenException;
import com.ms.member.mobileauth.service.model.MobileAuthTokenValidateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

/**
 * 전화번호 인증 토큰 검증 유즈케이스의 기본 구현 서비스.
 */
@Service
@RequiredArgsConstructor
public class ValidateMobileAuthTokenService implements ValidateMobileAuthTokenUseCase {

  private final MobileAuthApiClient mobileAuthApiClient;

  @Override
  public MobileAuthTokenValidateResult validate(ValidateMobileAuthTokenCommand command)
      throws InvalidMobileAuthTokenException {
    try {
      return MobileAuthTokenValidateResult.of(mobileAuthApiClient.validate(command.getToken()));
    } catch (RestClientException e) {
      throw new InvalidMobileAuthTokenException();
    }
  }
}
