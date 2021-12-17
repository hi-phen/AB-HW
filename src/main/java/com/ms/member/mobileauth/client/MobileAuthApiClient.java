package com.ms.member.mobileauth.client;

import java.util.UUID;
import org.springframework.stereotype.Component;

/**
 * 외부 전화번호 인증 서비스를 호출하는 클라이언트.
 * 실제로 호출하지는 않기 때문에 응답을 모킹한다.
 */
@Component
public class MobileAuthApiClient {

  /**
   * 전화번호 인증 시작 요청.
   *
   * @param mobile 전화번호.
   */
  public void requestMobileAuth(String mobile) {
    // do nothing
  }

  /**
   * 인증번호 인증 요청.
   *
   * @param mobile     전화 번호
   * @param authNumber 인증 번호
   * @return 항상 정상 인증을 가정하고 임의의 문자열(토큰)을 리턴한다.
   */
  public String authenticate(String mobile, String authNumber) {
    return UUID.randomUUID().toString();
  }

  /**
   * 인증토큰 검증 요청.
   *
   * @param token 전화번호 인증 토큰
   * @return 항상 정상 인증을 가정하고 빈 문자열을 리턴한다.
   */
  public String validate(String token) {
    return "";
  }
}
