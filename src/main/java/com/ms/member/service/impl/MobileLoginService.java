package com.ms.member.service.impl;

import com.ms.member.exception.MemberNotFoundException;
import com.ms.member.repository.MemberRepository;
import com.ms.member.service.LoginTokenCreateUseCase;
import com.ms.member.service.MemberLoginUseCase;
import com.ms.member.service.command.MemberLoginCommand;
import com.ms.member.service.domain.LoginKeyType;
import com.ms.member.service.model.LoginToken;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 전화번호를 이용한 로그인 서비스.
 */
@Service
@AllArgsConstructor
public class MobileLoginService implements MemberLoginUseCase {

  private final MemberRepository memberRepository;
  private final LoginTokenCreateUseCase loginTokenCreateUseCase;

  @Override
  public LoginToken login(MemberLoginCommand command) throws MemberNotFoundException {
    var member = memberRepository.findByMobileAndPassword(command.getKey(), command.getPassword())
        .orElseThrow(MemberNotFoundException::new);
    return loginTokenCreateUseCase.create(member);
  }

  @Override
  public LoginKeyType getLoginKeyType() {
    return LoginKeyType.MOBILE;
  }
}
