package com.ms.member.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.ms.member.entity.Member;
import com.ms.member.exception.MemberNotFoundException;
import com.ms.member.repository.MemberRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("회원 조회 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class MemberFindServiceTest {

  @Mock
  MemberRepository memberRepository;

  MemberFindService memberFindService;

  @BeforeEach
  void setUp() {
    this.memberFindService = new MemberFindService(memberRepository);
  }

  @Test
  @DisplayName("회원을 찾지 못하면 MemberNotFoundException 예외를 반환한다.")
  void notFound() {
    given(memberRepository.findById(anyLong())).willReturn(Optional.empty());

    assertThatThrownBy(() -> {
      memberFindService.findById(1L);
    }).isInstanceOf(MemberNotFoundException.class);
  }

  @Test
  @DisplayName("회원을 정상 조회 하면 회원 정보를 반환한다")
  void success() throws MemberNotFoundException {
    given(memberRepository.findById(anyLong())).willReturn(Optional.of(Member.of().id(1L).build()));
    var member = memberFindService.findById(1L);
    assertThat(member.getId()).isEqualTo(1L);
  }
}
