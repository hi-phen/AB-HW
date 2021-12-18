package com.ms.member.service.impl;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import com.ms.member.exception.DuplicateValueException;
import com.ms.member.repository.MemberRepository;
import com.ms.member.service.command.MemberPersistCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("회원 생성 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class MemberCreateServiceTest {

  @Mock
  MemberRepository memberRepository;

  MemberCreateService memberCreateService;

  @BeforeEach
  void setUp() {
    memberCreateService = new MemberCreateService(memberRepository);
  }

  MemberPersistCommand givenCommand() {
    return MemberPersistCommand.of()
        .email("email")
        .mobile("mobile")
        .name("name")
        .nickName("nickname")
        .password("password")
        .build();
  }

  @Nested
  @DisplayName("회원을 생성 할 때")
  class DescribeCreate {
    @DisplayName("이메일이 중복된다면 DuplicateValueException 을 반환한다")
    @Test
    void duplicateEmail() {

      given(memberRepository.existsByEmail(anyString())).willReturn(true);

      var command = givenCommand();

      assertThatThrownBy(() -> {
        memberCreateService.create(command);
      }).isInstanceOf(DuplicateValueException.class).hasMessage("이메일 값이 이미 있습니다.");

    }

    @DisplayName("전화번호가 중복된다면 DuplicateValueException 을 반환한다")
    @Test
    void duplicateMobile() {

      given(memberRepository.existsByMobile(anyString())).willReturn(true);

      var command = givenCommand();

      assertThatThrownBy(() -> {
        memberCreateService.create(command);
      }).isInstanceOf(DuplicateValueException.class).hasMessage("전화번호 값이 이미 있습니다.");

    }

    @DisplayName("중복값이 없다면 정상 처리 된다")
    @Test
    void create() throws DuplicateValueException {
      var command = givenCommand();
      memberCreateService.create(command);
    }

  }

}
