package com.ms.member.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import com.ms.member.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("로그인 토큰 생성 서비스 테스트")
class LoginTokenCreateServiceTest {

  @DisplayName("로그인 토큰 생성 테스트")
  @Test
  void create() {
    var loginTokenCreateService = new LoginTokenCreateService();
    var loginToken = loginTokenCreateService.create(Member.of().id(1L).build());
    assertThat(loginToken.getToken()).isEqualTo("1");
  }
}
