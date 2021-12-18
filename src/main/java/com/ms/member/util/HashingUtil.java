package com.ms.member.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 해싱 유틸.
 */
public class HashingUtil {

  private static String HASHING_ALGORITHM = "SHA-256";

  /**
   * 해싱.
   *
   * @param value 해싱 할 값
   * @return SHA-256으로 해싱 된 값
   */
  public static String hashing(String value) {
    try {
      var digest = MessageDigest.getInstance(HASHING_ALGORITHM);
      var hash = digest.digest(value.getBytes(StandardCharsets.UTF_8));
      return hashBytesToString(hash);
    } catch (NoSuchAlgorithmException e) {
      return value;
    }
  }

  private static String hashBytesToString(byte[] hashBytes) {
    StringBuilder hexString = new StringBuilder();
    for (byte hashByte : hashBytes) {
      hexString.append(String.format("%02x", hashByte));
    }
    return hexString.toString();
  }
}
