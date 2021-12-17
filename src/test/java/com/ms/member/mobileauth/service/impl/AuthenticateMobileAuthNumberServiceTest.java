package com.ms.member.mobileauth.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import com.ms.member.mobileauth.client.MobileAuthApiClient;
import com.ms.member.mobileauth.service.command.MobileAuthNumberAuthenticateCommand;
import com.ms.member.mobileauth.service.exception.InvalidAuthNumberException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;

@DisplayName("전화번호 인증번호 인증 테스트")
@ExtendWith({MockitoExtension.class})
class AuthenticateMobileAuthNumberServiceTest {

  @Mock
  MobileAuthApiClient mobileAuthApiClient;

  AuthenticateMobileAuthNumberService authenticateMobileAuthNumberService;

  @BeforeEach
  void setUp() {
    authenticateMobileAuthNumberService =
        new AuthenticateMobileAuthNumberService(mobileAuthApiClient);
  }

  @DisplayName("전화번호 인증번호로 인증을 성공하면 토큰을 반환한다.")
  @Test
  void validAuthenticate() throws InvalidAuthNumberException {

    given(mobileAuthApiClient.authenticate(anyString(), anyString())).willReturn("auth-token");

    var result = authenticateMobileAuthNumberService.authenticate(
        MobileAuthNumberAuthenticateCommand.of("010-1234-1234", "1234"));

    assertThat(result.getToken()).isEqualTo("auth-token");
  }

  @DisplayName("전화번호 인증번호로 인증을 실패하면 InvalidAuthNumberException 예외를 던진다.")
  @Test
  void inValidAuthenticate() {

    given(mobileAuthApiClient.authenticate(anyString(), anyString())).willThrow(
        new RestClientException(""));

    assertThatThrownBy(() -> authenticateMobileAuthNumberService.authenticate(
        MobileAuthNumberAuthenticateCommand.of("010-1234-1234", "1234")
    )).isInstanceOf(InvalidAuthNumberException.class);
  }
}
