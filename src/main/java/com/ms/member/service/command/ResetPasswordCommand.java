package com.ms.member.service.command;

import com.ms.member.util.HashingUtil;
import lombok.Builder;
import lombok.Value;

/**
 * 비밀번호 재설정 커맨드
 */
@Value(staticConstructor = "of")
public class ResetPasswordCommand {
  String mobile;
  String resetPassword;

  /**
   * 기본 생성자.
   *
   * @param mobile        전화번호
   * @param resetPassword 재설정 할 패스워드
   */
  @Builder(builderClassName = "Of", builderMethodName = "of")
  public ResetPasswordCommand(String mobile, String resetPassword) {
    this.mobile = mobile;
    this.resetPassword = resetPassword;
  }

  public String getResetPassword() {
    return HashingUtil.hashing(resetPassword);
  }
}
