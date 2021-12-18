package com.ms.member.service;

import com.ms.member.entity.Member;
import com.ms.member.service.model.LoginToken;

/**
 * 로그인 토큰 생성 유즈케이스.
 */
public interface LoginTokenCreateUseCase {
  /**
   * 로그인 토큰 생성.
   *
   * @param member 회원정보
   * @return 로그인 토큰
   */
  LoginToken create(Member member);
}
