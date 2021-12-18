package com.ms.member.web.model;

import javax.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PasswordResetRequest {
  @NotEmpty(message = "전화번호 인증 정보가 없습니다.")
  String mobileAuthToken;
  @NotEmpty(message = "패스워드가 입력되지 않았습니다.")
  String password;

  /**
   * 기본 생성자.
   *
   * @param mobileAuthToken 전화번호 인증 토큰
   * @param password        변경 할 패스워드
   */
  @Builder(builderClassName = "Of", builderMethodName = "of")
  public PasswordResetRequest(String mobileAuthToken, String password) {
    this.mobileAuthToken = mobileAuthToken;
    this.password = password;
  }
}
