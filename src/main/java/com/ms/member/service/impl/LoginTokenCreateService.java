package com.ms.member.service.impl;

import com.ms.member.entity.Member;
import com.ms.member.service.LoginTokenCreateUseCase;
import com.ms.member.service.model.LoginToken;
import org.springframework.stereotype.Service;

/**
 * 로그인 토큰 생성 유즈케이스의 기본 구현 서비스.
 */
@Service
public class LoginTokenCreateService implements LoginTokenCreateUseCase {
  @Override
  public LoginToken create(Member member) {
    return LoginToken.of(String.valueOf(member.getId()));
  }
}
