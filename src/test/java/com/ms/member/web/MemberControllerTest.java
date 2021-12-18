package com.ms.member.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.member.entity.Member;
import com.ms.member.exception.MemberNotFoundException;
import com.ms.member.mobileauth.service.ValidateMobileAuthTokenUseCase;
import com.ms.member.mobileauth.service.exception.InvalidMobileAuthTokenException;
import com.ms.member.service.MemberCreateUseCase;
import com.ms.member.service.MemberLoginUseCase;
import com.ms.member.service.domain.LoginKeyType;
import com.ms.member.service.impl.LoginServiceFactory;
import com.ms.member.service.model.LoginToken;
import com.ms.member.web.model.LoginRequest;
import com.ms.member.web.model.MemberCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@DisplayName("회원 컨트롤러 테스트")
@WebMvcTest(MemberController.class)
class MemberControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  MemberCreateUseCase memberCreateUseCase;

  @MockBean
  ValidateMobileAuthTokenUseCase validateMobileAuthTokenUseCase;

  @MockBean
  LoginServiceFactory loginServiceFactory;

  @Autowired
  ObjectMapper objectMapper;

  @Nested
  @DisplayName("회원을 생성 할 때")
  class DescribeCreate {

    MemberCreateRequest givenCreateRequest() {
      return MemberCreateRequest.of()
          .email("email@emali.com")
          .mobile("010-1234-1234")
          .mobileAuthToken("token")
          .name("name")
          .nickName("nick")
          .password("pwd")
          .build();
    }

    @DisplayName("전화번호 인증 토큰이 올바르지 않으면 422를 리턴한다")
    @Test
    void invalidMobileAuthToken() throws Exception {
      given(validateMobileAuthTokenUseCase.validate(any())).willThrow(
          new InvalidMobileAuthTokenException());

      var request = givenCreateRequest();

      mockMvc.perform(
              MockMvcRequestBuilders.post("/member/")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(request))
          ).andDo(print())
          .andExpect(status().isUnprocessableEntity());
    }

    @DisplayName("정상 처리를 하면 201을 응답한다")
    @Test
    void create() throws Exception {

      given(memberCreateUseCase.create(any())).willReturn(Member.of().id(1L).build());

      var request = givenCreateRequest();

      mockMvc.perform(
              MockMvcRequestBuilders.post("/member/")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(request))
          ).andDo(print())
          .andExpect(status().isCreated());
    }
  }

  @Nested
  @DisplayName("로그인을 할 때")
  class DescribeLogin {

    LoginRequest givenRequest(LoginKeyType loginKeyType) {
      return LoginRequest.of()
          .loginKeyType(loginKeyType)
          .key("key")
          .password("password")
          .build();
    }

    @DisplayName("로그인에 실패하면 401을 반환한다.")
    @Test
    void unAuthorize() throws Exception {

      var mockLoginUseCase = mock(MemberLoginUseCase.class);
      given(mockLoginUseCase.login(any())).willThrow(new MemberNotFoundException());
      given(loginServiceFactory.getLoginService(any())).willReturn(mockLoginUseCase);

      mockMvc.perform(
              MockMvcRequestBuilders.post("/member/login")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(givenRequest(LoginKeyType.EMAIL)))
          ).andDo(print())
          .andExpect(status().isUnauthorized());
    }

    @DisplayName("로그인에 성공하면 토큰을 반환한다.")
    @Test
    void success() throws Exception {

      var mockLoginUseCase = mock(MemberLoginUseCase.class);
      given(mockLoginUseCase.login(any())).willReturn(LoginToken.of("1"));
      given(loginServiceFactory.getLoginService(any())).willReturn(mockLoginUseCase);

      mockMvc.perform(
              MockMvcRequestBuilders.post("/member/login")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(givenRequest(LoginKeyType.EMAIL)))
          ).andDo(print())
          .andExpect(status().isOk())
          .andExpect(content().json("{\"token\":\"1\"}"));
    }
  }
}

