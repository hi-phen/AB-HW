package com.ms.member.service;

import com.ms.member.entity.Member;
import com.ms.member.exception.MemberNotFoundException;

/**
 * 회원 조회 유즈케이스.
 */
public interface MemberFindUseCase {
  /**
   * 회원 아이디로 조회.
   *
   * @param id 회원 아이디
   * @return 회원 정보
   * @throws MemberNotFoundException 회원을 찾지 못함
   */
  Member findById(Long id) throws MemberNotFoundException;
}
