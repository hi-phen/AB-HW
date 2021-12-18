package com.ms.member.web;

import static com.fasterxml.jackson.databind.node.JsonNodeType.STRING;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.member.entity.Member;
import com.ms.member.mobileauth.service.ValidateMobileAuthTokenUseCase;
import com.ms.member.service.MemberCreateUseCase;
import com.ms.member.web.model.MemberCreateRequest;
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

@DisplayName("회원 가입 컨트롤러 RestDoc")
@WebMvcTest(MemberCreateController.class)
@ExtendWith(RestDocumentationExtension.class)
class MemberCreateControllerRestDocTest {

  MockMvc mockMvc;

  @MockBean
  MemberCreateUseCase memberCreateUseCase;

  @MockBean
  ValidateMobileAuthTokenUseCase validateMobileAuthTokenUseCase;

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

  @DisplayName("회원 가입")
  @Test
  void create() throws Exception {

    given(memberCreateUseCase.create(any())).willReturn(Member.of().id(1L).build());

    var request = MemberCreateRequest.of()
        .email("email@emali.com")
        .mobile("010-1234-1234")
        .mobileAuthToken("token")
        .name("name")
        .nickName("nick")
        .password("pwd")
        .build();

    var resultActions = mockMvc.perform(
        RestDocumentationRequestBuilders.post("/member/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
    );

    resultActions.andExpect(status().isCreated())
        .andDo(
            document("{method-name}"
                , preprocessResponse(prettyPrint())
                , requestFields(
                    fieldWithPath("mobileAuthToken").type(STRING).description("전화번호 인증 토큰"),
                    fieldWithPath("mobile").type(STRING)
                        .attributes(key("format").value("`-`를 포함한 전화번호 형식"))
                        .description("전화번호"),
                    fieldWithPath("email").type(STRING).description("이메일"),
                    fieldWithPath("nickName").type(STRING).description("닉네임"),
                    fieldWithPath("password").type(STRING).description("비밀번호"),
                    fieldWithPath("name").type(STRING).description("이름")
                )
            )
        );
  }


}

