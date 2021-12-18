package com.ms.member.web;

import com.ms.member.exception.MemberNotFoundException;
import com.ms.member.exception.UnAuthorizedException;
import com.ms.member.service.MemberFindUseCase;
import com.ms.member.web.model.MemberInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * 회원 조회 컨트롤러.
 */
@RestController
@RequiredArgsConstructor
public class MemberController {

  private final MemberFindUseCase memberFindUseCase;

  /**
   * 회원 조회 (내정보 조회).
   *
   * @param id    회원 아이디
   * @param token 로그인 토큰
   * @return 회원 정보
   */
  @GetMapping("/member/{id}")
  ResponseEntity<?> getMember(@PathVariable Long id,
                              @RequestHeader("authorization") String token) {
    try {
      validateLoginToken(id, token);
      var member = memberFindUseCase.findById(id);
      return ResponseEntity.ok(
          MemberInfoResponse.of()
              .email(member.getEmail())
              .id(member.getId())
              .mobile(member.getMobile())
              .name(member.getName())
              .nickName(member.getNickName())
              .build()
      );
    } catch (UnAuthorizedException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    } catch (MemberNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  private void validateLoginToken(Long id, String token) throws UnAuthorizedException {
    if (!token.equals(String.valueOf(id))) {
      throw new UnAuthorizedException();
    }
  }

}
