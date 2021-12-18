package com.ms.member.web;

import com.ms.member.exception.DuplicateValueException;
import com.ms.member.mobileauth.service.ValidateMobileAuthTokenUseCase;
import com.ms.member.mobileauth.service.command.ValidateMobileAuthTokenCommand;
import com.ms.member.mobileauth.service.exception.InvalidMobileAuthTokenException;
import com.ms.member.service.MemberCreateUseCase;
import com.ms.member.service.command.MemberPersistCommand;
import com.ms.member.web.domain.MemberCreateRequest;
import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

  private final MemberCreateUseCase memberCreateUseCase;
  private final ValidateMobileAuthTokenUseCase validateMobileAuthTokenUseCase;

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/member")
  ResponseEntity<?> create(@RequestBody @Valid MemberCreateRequest request) {
    try {
      validateMobileAuthToken(request);

      var member = memberCreateUseCase.create(MemberPersistCommand.of()
          .email(request.getEmail())
          .mobile(request.getMobile())
          .name(request.getName())
          .nickName(request.getNickName())
          .password(request.getPassword())
          .build()
      );

      return ResponseEntity
          .created(URI.create(String.format("/member/%s", member.getId())))
          .build();
    } catch (InvalidMobileAuthTokenException | DuplicateValueException e) {
      return ResponseEntity.unprocessableEntity().body(e.getMessage());
    }
  }

  private void validateMobileAuthToken(MemberCreateRequest request)
      throws InvalidMobileAuthTokenException {
    validateMobileAuthTokenUseCase.validate(
        ValidateMobileAuthTokenCommand.of(request.getMobileAuthToken()));
  }

}
