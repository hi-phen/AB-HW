package com.ms.member.mobileauth.service.impl;

import com.ms.member.mobileauth.client.MobileAuthApiClient;
import com.ms.member.mobileauth.service.RequestMobileAuthUseCase;
import com.ms.member.mobileauth.service.command.MobileAuthRequestCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 전화번호 인증 요청 유즈케이스의 기본 구현 서비스.
 */
@Service
@RequiredArgsConstructor
public class RequestMobileAuthService implements RequestMobileAuthUseCase {

  private final MobileAuthApiClient mobileAuthApiClient;

  @Override
  public void request(MobileAuthRequestCommand command) {
    mobileAuthApiClient.requestMobileAuth(command.getMobile());
  }
}
