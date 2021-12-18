package com.ms.member.web.model;

import com.ms.member.service.domain.LoginKeyType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 로그인 요청.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginRequest {
  @NotNull(message = "로그인 방법이 지정되지 않았습니다.")
  LoginKeyType loginKeyType;
  @NotEmpty(message = "로그인 키가 입력되지 않았습니다.")
  String key;
  @NotEmpty(message = "패스워드가 입력되지 않았습니다.")
  String password;

  /**
   * 기본 생성자.
   *
   * @param loginKeyType 로그인 방법
   * @param key          로그인 키
   * @param password     패스워드
   */
  @Builder(builderClassName = "Of", builderMethodName = "of")
  public LoginRequest(LoginKeyType loginKeyType, String key, String password) {
    this.loginKeyType = loginKeyType;
    this.key = key;
    this.password = password;
  }
}
