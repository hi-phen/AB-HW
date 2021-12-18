package com.ms.member.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.ms.member.exception.LoginServiceNotFoundException;
import com.ms.member.repository.MemberRepository;
import com.ms.member.service.LoginTokenCreateUseCase;
import com.ms.member.service.domain.LoginKeyType;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("로그인 서비스 팩토리 테스트")
@ExtendWith(MockitoExtension.class)
class LoginServiceFactoryTest {

  LoginServiceFactory loginServiceFactory;

  @Mock
  MemberRepository memberRepository;

  @Mock
  LoginTokenCreateUseCase loginTokenCreateUseCase;

  @BeforeEach
  void setUp() {
    loginServiceFactory = new LoginServiceFactory(
        List.of(
            new MobileLoginService(memberRepository, loginTokenCreateUseCase),
            new EmailLoginService(memberRepository, loginTokenCreateUseCase)
        )
    );
  }

  @DisplayName("이메일 로그인 일때 이메일 로그인 서비스를 반환한다.")
  @Test
  void emailType() {
    var loginService = loginServiceFactory.getLoginService(LoginKeyType.EMAIL);
    assertThat(loginService).isInstanceOf(EmailLoginService.class);
  }

  @DisplayName("전화번호 로그인 일때 전화번호 로그인 서비스를 반환한다.")
  @Test
  void mobileType() {
    var loginService = loginServiceFactory.getLoginService(LoginKeyType.MOBILE);
    assertThat(loginService).isInstanceOf(MobileLoginService.class);
  }

  @DisplayName("서비스를 찾지 못하면 LoginServiceNotFoundException 예외를 반환한다.")
  @Test
  void notFound() {
    assertThatThrownBy(() -> {
      loginServiceFactory.getLoginService(null);
    }).isInstanceOf(LoginServiceNotFoundException.class);
  }
}
