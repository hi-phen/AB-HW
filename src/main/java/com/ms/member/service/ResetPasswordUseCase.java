package com.ms.member.service;

import com.ms.member.exception.MemberNotFoundException;
import com.ms.member.service.command.ResetPasswordCommand;

/**
 * 비밀번호 재설정 유즈케이스.
 */
public interface ResetPasswordUseCase {
  void reset(ResetPasswordCommand command) throws MemberNotFoundException;
}
