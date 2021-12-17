package com.ms.member.mobileauth.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ms.member.mobileauth.service.AuthenticateMobileAuthNumberUseCase;
import com.ms.member.mobileauth.service.RequestMobileAuthUseCase;
import com.ms.member.mobileauth.service.exception.InvalidAuthNumberException;
import com.ms.member.mobileauth.service.model.MobileAuthNumberAuthenticateResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@DisplayName("전화번호 인증 컨트롤러 테스트")
@WebMvcTest(MobileAuthController.class)
class MobileAuthControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  RequestMobileAuthUseCase requestMobileAuthUseCase;

  @MockBean
  AuthenticateMobileAuthNumberUseCase authenticateMobileAuthNumberUseCase;

  @Nested
  @DisplayName("전화번호 인증을 요청할 때")
  class DescribeRequestMobileAuth {

    @Nested
    @DisplayName("전화번호가 형식에 맞지 않으면")
    class ContextWithInvalidMobile {
      @DisplayName("StatusCode는 422를 리턴하고 오류 메세지를 반환한다.")
      @ValueSource(strings = {"",
          "1234",
          "01012341234",
          "0101234321",
          "010-12342334",
          "010-1234-123"})
      @ParameterizedTest
      void test(String invalidMobileNumber) throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/mobile-auth/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"mobile\":\"" + invalidMobileNumber + "\"}")
            ).andDo(print())
            .andExpect(status().isUnprocessableEntity());
      }
    }

    @Nested
    @DisplayName("전화번호가 형식에 맞으면")
    class ContextWithValidMobile {

      @DisplayName("StatusCode 200을 리턴한다.")
      @ValueSource(strings = {"010-1234-1234", "010-123-1234"})
      @ParameterizedTest
      void test(String validMobileNumber) throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/mobile-auth/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"mobile\":\"" + validMobileNumber + "\"}")
            ).andDo(print())
            .andExpect(status().isOk());
      }
    }
  }

  @Nested
  @DisplayName("인증번호로 인증토큰을 요청 할 때")
  class DescribeGetMobileAuthToken {
    @Nested
    @DisplayName("올바른 파라메터로 요청 하면")
    class ContextWithValidRequest {
      @DisplayName("StatusCode는 200을 리턴하고 인증토큰을 반환한다.")
      @Test
      void test() throws Exception {

        given(authenticateMobileAuthNumberUseCase.authenticate(any()))
            .willReturn(MobileAuthNumberAuthenticateResult.of("auth-token"));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/mobile-auth/token")
                    .param("mobile", "010-1234-1234")
                    .param("authNumber", "1234")
            ).andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json("{\"token\":\"auth-token\"}"));
      }
    }

    @Nested
    @DisplayName("인증에 실패 하면")
    class ContextWithAuthenticationFail {
      @DisplayName("StatusCode는 400을 리턴한다.")
      @Test
      void test() throws Exception {
        given(authenticateMobileAuthNumberUseCase.authenticate(any()))
            .willThrow(new InvalidAuthNumberException());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/mobile-auth/token")
                    .param("mobile", "010-1234-1234")
                    .param("authNumber", "1234")
            ).andDo(print())
            .andExpect(status().isBadRequest());
      }
    }
  }
}
