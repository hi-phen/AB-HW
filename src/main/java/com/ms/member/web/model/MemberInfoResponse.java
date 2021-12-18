package com.ms.member.web.model;

import lombok.Builder;
import lombok.Value;

/**
 * 회원 정보 응답.
 */
@Value(staticConstructor = "of")
public class MemberInfoResponse {
  long id;
  String email;
  String nickName;
  String name;
  String mobile;

  /**
   * 기본 생성자.
   *
   * @param id       아이디
   * @param email    이메일
   * @param nickName 닉네임
   * @param name     이름
   * @param mobile   전화번호
   */
  @Builder(builderClassName = "Of", builderMethodName = "of")
  public MemberInfoResponse(long id, String email, String nickName, String name,
                            String mobile) {
    this.id = id;
    this.email = email;
    this.nickName = nickName;
    this.name = name;
    this.mobile = mobile;
  }
}
