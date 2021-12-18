package com.ms.member.web;

import com.ms.member.exception.MemberNotFoundException;
import com.ms.member.exception.UnAuthorizedException;
import com.ms.member.mobileauth.service.ValidateMobileAuthTokenUseCase;
import com.ms.member.mobileauth.service.command.ValidateMobileAuthTokenCommand;
import com.ms.member.mobileauth.service.exception.InvalidMobileAuthTokenException;
import com.ms.member.service.ResetPasswordUseCase;
import com.ms.member.service.command.ResetPasswordCommand;
import com.ms.member.web.model.PasswordResetRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 회원 수정 컨트롤러.
 */
@RestController
@RequiredArgsConstructor
public class MemberUpdateController {

  private final ValidateMobileAuthTokenUseCase validateMobileAuthTokenUseCase;
  private final ResetPasswordUseCase resetPasswordUseCase;

  @PutMapping("/member/{mobile}")
  ResponseEntity<?> resetPassword(@RequestBody @Valid PasswordResetRequest request,
                                  @PathVariable String mobile) {
    try {
      validateMobileAuthTokenForResetPassword(request, mobile);
      resetPasswordUseCase.reset(ResetPasswordCommand.of()
          .mobile(mobile)
          .resetPassword(request.getPassword())
          .build());
      return ResponseEntity.ok().build();
    } catch (UnAuthorizedException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    } catch (InvalidMobileAuthTokenException e) {
      return ResponseEntity.unprocessableEntity().body(e.getMessage());
    } catch (MemberNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  private void validateMobileAuthTokenForResetPassword(PasswordResetRequest request, String mobile)
      throws UnAuthorizedException, InvalidMobileAuthTokenException {
    validateMobileAuthTokenUseCase.validate(
        ValidateMobileAuthTokenCommand.of(request.getMobileAuthToken()));
    if (!request.getMobileAuthToken().equals(mobile)) {
      throw new UnAuthorizedException();
    }
  }

}
