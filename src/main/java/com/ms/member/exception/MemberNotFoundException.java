package com.ms.member.exception;

/**
 * 회원 조회 실패 예외.
 */
public class MemberNotFoundException extends Exception {
  public MemberNotFoundException() {
    super("해당 회원을 찾을 수 없습니다.");
  }
}
