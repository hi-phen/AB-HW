package com.ms.member.repository;

import com.ms.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 회원 레포지토리.
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
  /**
   * 이메일과 패스워드로 회원 조회.
   *
   * @param email    이메일
   * @param password 패스워드
   * @return 회원 옵셔널
   */
  Optional<Member> findByEmailAndPassword(String email, String password);

  /**
   * 전화번호와 패스워드로 회원 조회.
   *
   * @param mobile   전화번호
   * @param password 패스워드
   * @return 회원 옵셔널
   */
  Optional<Member> findByMobileAndPassword(String mobile, String password);

  /**
   * 이메일이 이미 존재 하는지 조회.
   *
   * @param email 이메일
   * @return 존재 여부
   */
  boolean existsByEmail(String email);

  /**
   * 전화번호가 이미 존재하는지 조회.
   *
   * @param mobile 전화번호
   * @return 존재 여부
   */
  boolean existsByMobile(String mobile);
}
