package com.ms.member.service.impl;

import com.ms.member.exception.LoginServiceNotFoundException;
import com.ms.member.service.MemberLoginUseCase;
import com.ms.member.service.domain.LoginKeyType;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 적합한 로그인 서비스를 선택해주는 컴포넌트.
 */
@Component
@AllArgsConstructor
public class LoginServiceFactory {

  private final List<MemberLoginUseCase> memberLoginServices;

  /**
   * 로그인 방식에 따라 알맞은 로그인 서비스를 리턴한다.
   *
   * @param loginKeyType 로그인 방식
   * @return 로그인 서비스
   */
  public MemberLoginUseCase getLoginService(LoginKeyType loginKeyType) {
    return memberLoginServices
        .stream()
        .filter(service -> service.getLoginKeyType().equals(loginKeyType))
        .findFirst()
        .orElseThrow(LoginServiceNotFoundException::new);
  }
}
