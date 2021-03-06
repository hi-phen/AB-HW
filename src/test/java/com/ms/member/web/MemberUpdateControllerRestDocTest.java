package com.ms.member.web;

import static com.fasterxml.jackson.databind.node.JsonNodeType.STRING;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.member.mobileauth.service.ValidateMobileAuthTokenUseCase;
import com.ms.member.service.ResetPasswordUseCase;
import com.ms.member.web.model.PasswordResetRequest;
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

@DisplayName("?????? ???????????? ???????????? RestDoc")
@WebMvcTest(MemberUpdateController.class)
@ExtendWith(RestDocumentationExtension.class)
class MemberUpdateControllerRestDocTest {

  MockMvc mockMvc;

  @MockBean
  ValidateMobileAuthTokenUseCase validateMobileAuthTokenUseCase;

  @Autowired
  ObjectMapper objectMapper;

  @MockBean
  ResetPasswordUseCase resetPasswordUseCase;

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

  @DisplayName("???????????? ?????????")
  @Test
  void resetPassword() throws Exception {

    var givenRequest = PasswordResetRequest.of()
        .mobileAuthToken("010-1234-1234").password("pwd").build();

    var resultActions = mockMvc.perform(
        RestDocumentationRequestBuilders.put("/member/{mobile}", "010-1234-1234")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(givenRequest))
    );

    resultActions.andExpect(status().isOk())
        .andDo(
            document("{method-name}"
                , preprocessResponse(prettyPrint())
                , pathParameters(
                    parameterWithName("mobile").description("????????????")
                )
                , requestFields(
                    fieldWithPath("mobileAuthToken").type(STRING)
                        .description("???????????? ?????? ?????? (???????????? ?????? ?????? ?????? ?????????)"),
                    fieldWithPath("password").type(STRING).description("?????? ??? ????????????")
                )
            )
        );
  }


}

