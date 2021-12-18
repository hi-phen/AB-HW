package com.ms.member.mobileauth.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.member.mobileauth.service.AuthenticateMobileAuthNumberUseCase;
import com.ms.member.mobileauth.service.RequestMobileAuthUseCase;
import com.ms.member.mobileauth.service.model.MobileAuthNumberAuthenticateResult;
import com.ms.member.mobileauth.web.model.RequestMobileAuthRequest;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@DisplayName("전화번호 인증 컨트롤러 RestDoc")
@WebMvcTest(MobileAuthController.class)
@ExtendWith(RestDocumentationExtension.class)
class MobileAuthControllerRestDocTest {

  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @MockBean
  RequestMobileAuthUseCase requestMobileAuthUseCase;

  @MockBean
  AuthenticateMobileAuthNumberUseCase authenticateMobileAuthNumberUseCase;

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

  @DisplayName("전화번호 인증 요청")
  @Test
  void requestMobileAuth() throws Exception {


    var givenRequest = RequestMobileAuthRequest.of()
        .mobile("010-1234-1234")
        .build();

    var resultActions = mockMvc.perform(
        RestDocumentationRequestBuilders.post("/mobile-auth/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(givenRequest))
    );

    resultActions.andExpect(status().isOk())
        .andDo(
            document("{method-name}"
                , preprocessResponse(prettyPrint())
                , requestFields(
                    fieldWithPath("mobile")
                        .attributes(key("format").value("`-`를 포함한 전화번호 형식"))
                        .description("전화번호")
                )
            )
        );
  }

  @DisplayName("인증 토큰 요청")
  @Test
  void getMobileAuthToken() throws Exception {

    given(authenticateMobileAuthNumberUseCase.authenticate(any()))
        .willReturn(MobileAuthNumberAuthenticateResult.of("auth-token"));
    
    var resultActions = mockMvc.perform(
        RestDocumentationRequestBuilders.get("/mobile-auth/token")
            .param("mobile", "010-1234-1234")
            .param("authNumber", "1234")
    );

    resultActions.andExpect(status().isOk())
        .andDo(
            document("{method-name}"
                , preprocessResponse(prettyPrint())
                , requestParameters(
                    parameterWithName("mobile")
                        .attributes(key("format").value("`-`를 포함한 전화번호 형식"))
                        .description("전화번호"),
                    parameterWithName("authNumber").description("인증 번호")
                )
                , responseFields(
                    fieldWithPath("token").type(STRING).description("전화번호 인증 토큰")
                )
            )
        );
  }

}

