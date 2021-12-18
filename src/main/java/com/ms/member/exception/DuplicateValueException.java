package com.ms.member.exception;

/**
 * 중복값 예외.
 */
public class DuplicateValueException extends Exception {
  /**
   * 기본 생성자.
   *
   * @param valueName 중복값 이름
   */
  public DuplicateValueException(String valueName) {
    super(String.format("%s 값이 이미 있습니다.", valueName));
  }
}
