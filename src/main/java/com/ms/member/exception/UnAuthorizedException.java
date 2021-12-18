package com.ms.member.exception;

/**
 * 인증 실패 예외.
 */
public class UnAuthorizedException extends Exception {
  public UnAuthorizedException() {
    super("인증정보가 올바르지 않습니다.");
  }
}
