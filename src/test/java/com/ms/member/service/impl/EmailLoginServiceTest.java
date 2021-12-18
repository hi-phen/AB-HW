package com.ms.member.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import com.ms.member.entity.Member;
import com.ms.member.exception.MemberNotFoundException;
import com.ms.member.repository.MemberRepository;
import com.ms.member.service.LoginTokenCreateUseCase;
import com.ms.member.service.command.MemberLoginCommand;
import com.ms.member.service.model.LoginToken;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * 이메일 로그인 서비스 테스트
 */
@ExtendWith(MockitoExtension.class)
class EmailLoginServiceTest {

  @Mock
  MemberRepository memberRepository;

  @Mock
  LoginTokenCreateUseCase loginTokenCreateUseCase;


  EmailLoginService emailLoginService;

  @BeforeEach
  void setUp() {
    emailLoginService = new EmailLoginService(memberRepository, loginTokenCreateUseCase);
  }

  @Nested
  @DisplayName("로그인 할 때")
  class DescribeLogin {
    @DisplayName("회원이 조회 되지 않으면 MemberNotFoundException 예외를 반환한다.")
    @Test
    void userNotFound() {
      given(memberRepository.findByEmailAndPassword(anyString(), anyString()))
          .willReturn(Optional.empty());

      assertThatThrownBy(() -> {
        emailLoginService.login(MemberLoginCommand.of("email", "password"));
      }).isInstanceOf(MemberNotFoundException.class);
    }

    @DisplayName("정상 조회 되면 로그인 토큰을 반환한다.")
    @Test
    void login() throws MemberNotFoundException {
      given(memberRepository.findByEmailAndPassword(anyString(), anyString()))
          .willReturn(Optional.of(Member.of().id(1L).build()));

      given(loginTokenCreateUseCase.create(any())).willReturn(LoginToken.of("1"));

      var token = emailLoginService.login(MemberLoginCommand.of("email", "password"));

      assertThat(token.getToken()).isEqualTo("1");
    }
  }
}
