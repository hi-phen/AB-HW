package com.ms.member.service;

import com.ms.member.exception.MemberNotFoundException;
import com.ms.member.service.command.MemberLoginCommand;
import com.ms.member.service.domain.LoginKeyType;
import com.ms.member.service.model.LoginToken;

/**
 * 회원 로그인 유즈 케이스.
 */
public interface MemberLoginUseCase {

  LoginToken login(MemberLoginCommand command) throws MemberNotFoundException;

  LoginKeyType getLoginKeyType();
}
