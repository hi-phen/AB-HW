package com.ms.member.web;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ms.member.entity.Member;
import com.ms.member.exception.MemberNotFoundException;
import com.ms.member.mobileauth.service.ValidateMobileAuthTokenUseCase;
import com.ms.member.service.MemberFindUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@DisplayName("회원 조회 컨트롤러 테스트")
@WebMvcTest(MemberController.class)
class MemberControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  ValidateMobileAuthTokenUseCase validateMobileAuthTokenUseCase;

  @MockBean
  MemberFindUseCase memberFindUseCase;

  @Nested
  @DisplayName("내정보를 조회 할 때")
  class DescribeGetMember {
    @DisplayName("로그인 토큰과 조회 회원 아이디가 일치 하지 않으면 401을 리턴한다.")
    @Test
    void unAuthorized() throws Exception {
      mockMvc.perform(
              MockMvcRequestBuilders.get("/member/1").
                  header("Authorization", "2")
          ).andDo(print())
          .andExpect(status().isUnauthorized());

    }

    @DisplayName("회원을 찾지 못하면 404를 리턴합니다.")
    @Test
    void notFound() throws Exception {

      given(memberFindUseCase.findById(anyLong())).willThrow(new MemberNotFoundException());

      mockMvc.perform(
              MockMvcRequestBuilders.get("/member/1").
                  header("Authorization", "1")
          ).andDo(print())
          .andExpect(status().isNotFound());
    }

    @DisplayName("정상 조회 하면 회원 정보를 리턴합니다.")
    @Test
    void success() throws Exception {
      given(memberFindUseCase.findById(anyLong())).willReturn(
          Member.of().id(1L).build()
      );

      mockMvc.perform(
              MockMvcRequestBuilders.get("/member/1").
                  header("Authorization", "1")
          ).andDo(print())
          .andExpect(status().isOk());
    }

  }

}

