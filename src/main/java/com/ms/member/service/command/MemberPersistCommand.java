package com.ms.member.service.command;

import com.ms.member.util.HashingUtil;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * 회원 저장 커맨드.
 */

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MemberPersistCommand {

  Long id;
  String email;
  String nickName;
  String password;
  String name;
  String mobile;

  /**
   * 기본 생성자.
   *
   * @param id       아이디
   * @param email    이메일
   * @param nickName 닉네임
   * @param password 비밀번호
   * @param name     이름
   * @param mobile   전화번호
   */
  @Builder(builderClassName = "Of", builderMethodName = "of")
  public MemberPersistCommand(Long id, String email, String nickName, String password,
                              String name,
                              String mobile) {
    this.id = id;
    this.email = email;
    this.nickName = nickName;
    this.password = password;
    this.name = name;
    this.mobile = mobile;
  }

  /**
   * 패스워드는 무조건 해싱 된 값을 반환한다.
   *
   * @return 해싱 된 패스워드
   */
  public String getPassword() {
    return HashingUtil.hashing(password);
  }
}
