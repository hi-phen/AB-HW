package com.ms.member.service.impl;

import com.ms.member.entity.Member;
import com.ms.member.exception.MemberNotFoundException;
import com.ms.member.repository.MemberRepository;
import com.ms.member.service.MemberFindUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 회원 조회 유즈케이스의 기본 구현 서비스.
 */
@Service
@RequiredArgsConstructor
public class MemberFindService implements MemberFindUseCase {

  private final MemberRepository memberRepository;

  @Override
  public Member findById(Long id) throws MemberNotFoundException {
    return memberRepository.findById(id)
        .orElseThrow(MemberNotFoundException::new);
  }
}
