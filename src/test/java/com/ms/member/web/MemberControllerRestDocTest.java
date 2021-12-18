package com.ms.member.web;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ms.member.entity.Member;
import com.ms.member.mobileauth.service.ValidateMobileAuthTokenUseCase;
import com.ms.member.service.MemberFindUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@DisplayName("회원 조회 컨트롤러 RestDoc")
@WebMvcTest(MemberController.class)
@ExtendWith(RestDocumentationExtension.class)
class MemberControllerRestDocTest {

  MockMvc mockMvc;

  @MockBean
  ValidateMobileAuthTokenUseCase validateMobileAuthTokenUseCase;

  @MockBean
  MemberFindUseCase memberFindUseCase;

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

  @DisplayName("내 정보 조회")
  @Test
  void getMember() throws Exception {

    var givenMember = Member.of()
        .id(1L)
        .nickName("닉네임")
        .name("이름")
        .mobile("010-1234-1234")
        .email("foo@bar.com")
        .build();

    given(memberFindUseCase.findById(anyLong())).willReturn(givenMember);

    var resultActions = mockMvc.perform(
        RestDocumentationRequestBuilders.get("/member/{id}", 1)
            .header("Authorization", "1")
    );

    resultActions.andExpect(status().isOk())
        .andDo(
            document("{method-name}"
                , preprocessResponse(prettyPrint())
                , requestHeaders(
                    headerWithName("Authorization").description("로그인 인증 토큰 (아이디 값과 동일해야 합니다)")
                )
                , pathParameters(
                    parameterWithName("id").description("아이디")
                )
                , responseFields(
                    fieldWithPath("id").type(NUMBER).description("아이디"),
                    fieldWithPath("email").type(STRING).description("이메일"),
                    fieldWithPath("nickName").type(STRING).description("닉네임"),
                    fieldWithPath("name").type(STRING).description("이름"),
                    fieldWithPath("mobile").type(STRING)
                        .attributes(key("format").value("`-`를 포함한 전화번호 형식"))
                        .description("전화번호")
                )
            )
        );
  }


}

