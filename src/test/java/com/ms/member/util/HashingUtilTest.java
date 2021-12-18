package com.ms.member.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("해싱 유틸 테스트")
class HashingUtilTest {

  @DisplayName("해싱")
  @Test
  void hashing() {
    String givenValue = "SOME VALUE";
    assertThat(HashingUtil.hashing(givenValue)).isNotEqualTo(givenValue);
  }
}
