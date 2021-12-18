package com.ms.member.web.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원 가입(생성) 요청.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberCreateRequest {
  @NotEmpty(message = "전화번호 인증 정보가 없습니다.")
  String mobileAuthToken;
  @NotEmpty(message = "전화번호가 입력되지 않았습니다.")
  @Pattern(regexp = "[0-9]{3}-[0-9]{3,4}-[0-9]{4}", message = "전화번호 형식이 올바르지 않습니다.")
  String mobile;
  @NotEmpty(message = "이메일이 입력되지 않았습니다.")
  @Pattern(
      regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$",
      message = "이메일 형식이 올바르지 않습니다.")
  String email;
  @NotEmpty(message = "닉네임이 입력되지 않았습니다.")
  String nickName;
  @NotEmpty(message = "패스워드가 입력되지 않았습니다.")
  String password;
  @NotEmpty(message = "이름이 입력되지 않았습니다.")
  String name;

  /**
   * 기본 생성자.
   *
   * @param mobileAuthToken 전화번호 인증 토큰
   * @param mobile          전화번호
   * @param email           이메일
   * @param nickName        닉네임
   * @param password        패스워드
   * @param name            이름
   */
  @Builder(builderMethodName = "of", builderClassName = "Of")
  public MemberCreateRequest(String mobileAuthToken, String mobile, String email,
                             String nickName, String password, String name) {
    this.mobileAuthToken = mobileAuthToken;
    this.mobile = mobile;
    this.email = email;
    this.nickName = nickName;
    this.password = password;
    this.name = name;
  }
}
