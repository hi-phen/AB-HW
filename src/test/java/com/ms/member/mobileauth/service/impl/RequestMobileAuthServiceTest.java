package com.ms.member.mobileauth.service.impl;

import com.ms.member.mobileauth.client.MobileAuthApiClient;
import com.ms.member.mobileauth.service.command.MobileAuthRequestCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("전화번호 인증 요청 테스트")
@ExtendWith({MockitoExtension.class})
class RequestMobileAuthServiceTest {

  @Mock
  MobileAuthApiClient mobileAuthApiClient;

  RequestMobileAuthService requestMobileAuthService;

  @BeforeEach
  void setUp() {
    requestMobileAuthService =
        new RequestMobileAuthService(mobileAuthApiClient);
  }

  @DisplayName("전화번호 인증 요청을 성공한다.")
  @Test
  void request() {
    requestMobileAuthService.request(MobileAuthRequestCommand.of("010-1234-1234"));
  }
}