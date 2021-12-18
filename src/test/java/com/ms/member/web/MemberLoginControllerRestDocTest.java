package com.ms.member.web;

import static com.fasterxml.jackson.databind.node.JsonNodeType.STRING;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.member.service.MemberLoginUseCase;
import com.ms.member.service.domain.LoginKeyType;
import com.ms.member.service.impl.LoginServiceFactory;
import com.ms.member.service.model.LoginToken;
import com.ms.member.web.model.LoginRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@DisplayName("회원 로그인 컨트롤러 RestDoc")
@WebMvcTest(MemberLoginController.class)
@ExtendWith(RestDocumentationExtension.class)
class MemberLoginControllerRestDocTest {

  MockMvc mockMvc;

  @MockBean
  LoginServiceFactory loginServiceFactory;

  @Autowired
  ObjectMapper objectMapper;

  @BeforeEach
  void setUp(WebApplicationContext webApplicationContext,
             RestDocumentationContextProvider restDocumentation) {
    this.mockMvc = MockMvcBuilders
        .webAppContextSetup(webApplicationContext)
        .addFilter(new CharacterEncodingFilter("UTF-8", true))
        .apply(documentationConfiguration(restDocumentation))
        .alwaysDo(print())
        .build();
  }

  LoginRequest givenRequest() {
    return LoginRequest.of()
        .loginKeyType(LoginKeyType.EMAIL)
        .key("key")
        .password("password")
        .build();
  }

  @DisplayName("로그인")
  @Test
  void login() throws Exception {

    var mockLoginUseCase = mock(MemberLoginUseCase.class);
    given(mockLoginUseCase.login(any())).willReturn(LoginToken.of("1"));
    given(loginServiceFactory.getLoginService(any())).willReturn(mockLoginUseCase);

    var resultActions = mockMvc.perform(
        RestDocumentationRequestBuilders.post("/member/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(givenRequest()))
    );

    resultActions.andExpect(status().isOk())
        .andDo(
            document("{method-name}"
                , preprocessResponse(prettyPrint())
                , requestFields(
                    fieldWithPath("loginKeyType").type(STRING)
                        .description("로그인 방법 <<LoginKeyType,[상세]>>"),
                    fieldWithPath("key").type(STRING).description("로그인 키 값 (아이디 또는 이메일)"),
                    fieldWithPath("password").type(STRING).description("비밀번호")
                )
                , responseFields(
                    fieldWithPath("token").type(JsonFieldType.STRING).description("로그인 토큰")
                )
            )
        );
  }


}

