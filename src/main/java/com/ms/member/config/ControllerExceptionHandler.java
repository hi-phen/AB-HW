package com.ms.member.config;

import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(BindException.class)
  ResponseEntity<String> serverErrorHandler(BindException e) {
    var message =
        e.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(
            Collectors.joining(","));
    return ResponseEntity.unprocessableEntity().body(message);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  ResponseEntity<String> serverErrorHandler(MethodArgumentNotValidException e) {
    var message = e.getBindingResult().getFieldErrors().stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(
            Collectors.joining(","));
    return ResponseEntity.unprocessableEntity().body(message);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  ResponseEntity<String> serverErrorHandler(ConstraintViolationException e) {
    var message = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
        .collect(Collectors.joining(","));
    return ResponseEntity.unprocessableEntity().body(message);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  ResponseEntity<String> serverErrorHandler(HttpMessageNotReadableException e) {
    return ResponseEntity.badRequest().body(e.getMessage());
  }
}
