package com.ms.member.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import com.ms.member.entity.Member;
import com.ms.member.exception.MemberNotFoundException;
import com.ms.member.repository.MemberRepository;
import com.ms.member.service.command.ResetPasswordCommand;
import com.ms.member.util.HashingUtil;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("비밀번호 재설정 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class ResetPasswordServiceTest {

  @Mock
  MemberRepository memberRepository;

  ResetPasswordService resetPasswordService;

  @BeforeEach
  void setUp() {
    resetPasswordService = new ResetPasswordService(memberRepository);
  }

  @DisplayName("회원을 찾지 못하면 MemberNotFoundException 예외를 반환한다")
  @Test
  void notFound() {
    given(memberRepository.findByMobile(anyString())).willReturn(Optional.empty());

    assertThatThrownBy(() -> {
      resetPasswordService.reset(ResetPasswordCommand.of("010-1234-1234", "PWD"));
    }).isInstanceOf(MemberNotFoundException.class);
  }

  @DisplayName("정상처리 하면 패스워드가 변경된다")
  @Test
  void success() throws MemberNotFoundException {
    var originalPassword = "pwd";
    var newPassword = "PWD";
    var givenMember = Member.of().password(originalPassword).build();
    given(memberRepository.findByMobile(anyString())).willReturn(Optional.of(givenMember));

    resetPasswordService.reset(ResetPasswordCommand.of("010-1234-1234", newPassword));

    assertThat(givenMember.getPassword()).isEqualTo(HashingUtil.hashing(newPassword));
  }

}
