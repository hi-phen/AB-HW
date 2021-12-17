package com.ms.member.mobileauth.service.exception;

/**
 * 잘못 된 인증토큰 예외.
 */
public class InvalidMobileAuthTokenException extends Exception {
  public InvalidMobileAuthTokenException() {
    super("올바르지 않은 인증 토큰입니다.");
  }
}
