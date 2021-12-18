package com.ms.member.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.ms.member.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DisplayName("회원 레포지토리 테스트")
@DataJpaTest
class MemberRepositoryTest {

  @Autowired
  MemberRepository memberRepository;

  @Autowired
  TestEntityManager testEntityManager;

  Member givenMember() {
    return Member.of()
        .email("member@email.com")
        .mobile("010-1234-1234")
        .name("이름")
        .nickName("닉네임")
        .password("비밀번호")
        .build();
  }

  @DisplayName("Create 테스트")
  @Test
  void create() {
    var member = givenMember();
    memberRepository.save(member);
    assertThat(member.getId()).isNotZero();
  }

  @DisplayName("Read 테스트")
  @Test
  void read() {
    var member = givenMember();
    memberRepository.save(member);
    testEntityManager.flush();
    testEntityManager.clear();
    assertThat(memberRepository.getById(member.getId()).getId()).isEqualTo(member.getId());
  }

  @DisplayName("Update 테스트")
  @Test
  void update() {
    var member = givenMember();
    memberRepository.save(member);
    member.setEmail("email");
    memberRepository.save(member);
    assertThat(memberRepository.getById(member.getId()).getEmail()).isEqualTo(member.getEmail());
  }

  @DisplayName("Delete 테스트")
  @Test
  void delete() {
    var member = givenMember();
    memberRepository.save(member);
    memberRepository.delete(member);
  }
}
