package com.ms.member.service.command;

import com.ms.member.util.HashingUtil;
import lombok.Value;

@Value(staticConstructor = "of")
public class MemberLoginCommand {
  
  String key;
  String password;

  /**
   * 기본 생성자.
   *
   * @param key      로그인 키
   * @param password 비밀번호
   */
  public MemberLoginCommand(String key, String password) {
    this.key = key;
    this.password = password;
  }

  public String getPassword() {
    return HashingUtil.hashing(password);
  }
}
