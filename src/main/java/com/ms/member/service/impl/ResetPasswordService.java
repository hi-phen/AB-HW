package com.ms.member.service.impl;

import com.ms.member.exception.MemberNotFoundException;
import com.ms.member.repository.MemberRepository;
import com.ms.member.service.ResetPasswordUseCase;
import com.ms.member.service.command.ResetPasswordCommand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 비밀번호 재설정 유즈케이스의 기본 구현 서비스.
 */
@Service
@AllArgsConstructor
public class ResetPasswordService implements ResetPasswordUseCase {

  private final MemberRepository repository;

  @Transactional
  @Override
  public void reset(ResetPasswordCommand command) throws MemberNotFoundException {
    var member =
        repository.findByMobile(command.getMobile()).orElseThrow(MemberNotFoundException::new);
    member.setPassword(command.getResetPassword());
  }
}
