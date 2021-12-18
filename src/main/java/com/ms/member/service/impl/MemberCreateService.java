package com.ms.member.service.impl;

import com.ms.member.entity.Member;
import com.ms.member.exception.DuplicateValueException;
import com.ms.member.repository.MemberRepository;
import com.ms.member.service.MemberCreateUseCase;
import com.ms.member.service.command.MemberPersistCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 회원 저장 유즈케이스의 기본 구현 서비스.
 */
@Service
@RequiredArgsConstructor
public class MemberCreateService implements MemberCreateUseCase {

  private final MemberRepository memberRepository;

  @Override
  public Member create(MemberPersistCommand command) throws DuplicateValueException {

    if (isDuplicateEmail(command.getEmail())) {
      throw new DuplicateValueException("이메일");
    }

    if (isDuplicateMobile(command.getMobile())) {
      throw new DuplicateValueException("전화번호");
    }

    return memberRepository.save(
        Member.of()
            .email(command.getEmail())
            .mobile(command.getMobile())
            .name(command.getName())
            .nickName(command.getNickName())
            .password(command.getPassword())
            .build()
    );
  }

  private boolean isDuplicateEmail(String email) {
    return memberRepository.existsByEmail(email);
  }

  private boolean isDuplicateMobile(String mobile) {
    return memberRepository.existsByMobile(mobile);
  }
}
