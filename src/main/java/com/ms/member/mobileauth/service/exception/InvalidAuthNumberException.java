package com.ms.member.mobileauth.service.exception;

/**
 * 잘못 된 인증번호 예외.
 */
public class InvalidAuthNumberException extends Exception {
  public InvalidAuthNumberException() {
    super("올바르지 않은 인증번호 입니다.");
  }
}
