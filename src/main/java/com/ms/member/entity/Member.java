package com.ms.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 회원 엔티티.
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@DynamicUpdate
@Table(indexes = {
    @Index(columnList = "email, password", unique = true),
    @Index(columnList = "mobile, password", unique = true),
    @Index(columnList = "email", unique = true),
    @Index(columnList = "mobile", unique = true)
})
public class Member {

  @Id
  @GeneratedValue
  Long id;

  @Column(nullable = false)
  String email;

  @Column(nullable = false)
  String nickName;

  @Column(nullable = false)
  String password;

  @Column(nullable = false)
  String name;

  @Column(nullable = false)
  String mobile;

  /**
   * 기본 생성자.
   *
   * @param id       고유 아이디
   * @param email    이메일
   * @param nickName 닉네임
   * @param password 비밀번호
   * @param name     이름
   * @param mobile   전화번호
   */
  @Builder(builderClassName = "Of", builderMethodName = "of")
  public Member(Long id, String email, String nickName, String password, String name,
                String mobile) {
    this.id = id;
    this.email = email;
    this.nickName = nickName;
    this.password = password;
    this.name = name;
    this.mobile = mobile;
  }
}
