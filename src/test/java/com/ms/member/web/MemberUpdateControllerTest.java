package com.ms.member.web;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.member.mobileauth.service.ValidateMobileAuthTokenUseCase;
import com.ms.member.service.ResetPasswordUseCase;
import com.ms.member.web.model.PasswordResetRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@DisplayName("회원 수정 컨트롤러 테스트")
@WebMvcTest(MemberUpdateController.class)
class MemberUpdateControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  ValidateMobileAuthTokenUseCase validateMobileAuthTokenUseCase;

  @Autowired
  ObjectMapper objectMapper;

  @MockBean
  ResetPasswordUseCase resetPasswordUseCase;

  @Nested
  @DisplayName("패스워드를 재설정 할 때")
  class DescribeResetPassword {
    PasswordResetRequest givenRequest() {
      return PasswordResetRequest.of()
          .mobileAuthToken("010-1234-1234").password("pwd").build();
    }

    @DisplayName("전화번호 인증 토큰과 전화번호가 일치하지 않으면 401를 리턴한다")
    @Test
    void unAuthorized() throws Exception {
      mockMvc.perform(
              MockMvcRequestBuilders.put("/member/010-1234-4321")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(givenRequest()))
          ).andDo(print())
          .andExpect(status().isUnauthorized());
    }

    @DisplayName("정상 처리를 하면 200을 응답한다")
    @Test
    void success() throws Exception {
      mockMvc.perform(
              MockMvcRequestBuilders.put("/member/010-1234-1234")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(givenRequest()))
          ).andDo(print())
          .andExpect(status().isOk());
    }
  }

}

