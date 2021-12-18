package com.ms.member.exception;

/**
 * 로그인 서비스 조회 실패 예외.
 */
public class LoginServiceNotFoundException extends RuntimeException {
  public LoginServiceNotFoundException() {
    super("올바르지 않는 로그인 방법입니다.");
  }
}
