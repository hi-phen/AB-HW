package com.ms.member.mobileauth.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import com.ms.member.mobileauth.client.MobileAuthApiClient;
import com.ms.member.mobileauth.service.command.ValidateMobileAuthTokenCommand;
import com.ms.member.mobileauth.service.exception.InvalidMobileAuthTokenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;

@DisplayName("전화번호 인증 토큰 인증 테스트")
@ExtendWith({MockitoExtension.class})
class ValidateMobileAuthTokenServiceTest {

  @Mock
  MobileAuthApiClient mobileAuthApiClient;

  ValidateMobileAuthTokenService validateMobileAuthTokenService;

  @BeforeEach
  void setUp() {
    validateMobileAuthTokenService =
        new ValidateMobileAuthTokenService(mobileAuthApiClient);
  }

  private String givenMobile() {
    return "010-1234-1234";
  }

  @DisplayName("토큰 인증에 성공하면 전화번호를 포함한 인증 결과를 반환한다.")
  @Test
  void validate() throws InvalidMobileAuthTokenException {
    given(mobileAuthApiClient.validate("auth-token")).willReturn(givenMobile());

    var result =
        validateMobileAuthTokenService.validate(ValidateMobileAuthTokenCommand.of("auth-token"));

    assertThat(result.getMobile()).isEqualTo(givenMobile());
  }

  @DisplayName("토큰 인증에 실패하면 InvalidMobileAuthTokenException 예외를 던진다.")
  @Test
  void inValidate() {
    given(mobileAuthApiClient.validate("auth-token")).willThrow(new RestClientException(""));

    assertThatThrownBy(() -> {
      validateMobileAuthTokenService.validate(ValidateMobileAuthTokenCommand.of("auth-token"));
    }).isInstanceOf(InvalidMobileAuthTokenException.class);

  }
}