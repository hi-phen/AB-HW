package com.ms.member.service;


import com.ms.member.entity.Member;
import com.ms.member.exception.DuplicateValueException;
import com.ms.member.service.command.MemberPersistCommand;

/**
 * 회원 생성 유즈케이스.
 */
public interface MemberCreateUseCase {
  /**
   * 회원 생성.
   *
   * @param command 회원 생성 커맨드
   * @throws DuplicateValueException 중복값 예외
   */
  Member create(MemberPersistCommand command) throws DuplicateValueException;

}
