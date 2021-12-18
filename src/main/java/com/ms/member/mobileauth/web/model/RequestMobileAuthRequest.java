package com.ms.member.mobileauth.web.model;

import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 전화번호 인증 요청.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestMobileAuthRequest {

  @Pattern(regexp = "[0-9]{3}-[0-9]{3,4}-[0-9]{4}", message = "전화번호 형식이 올바르지 않습니다.")
  private String mobile;

  /**
   * 기본 생성자.
   *
   * @param mobile 전화번호
   */
  @Builder(builderClassName = "Of", builderMethodName = "of")
  public RequestMobileAuthRequest(String mobile) {
    this.mobile = mobile;
  }
}
