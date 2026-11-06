package com.refresca_sp.infra.exception;

import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<DefaultErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
      .body(DefaultErrorResponse.of(
        HttpStatus.NOT_FOUND.value(),
        "Not Found",
        ex.getMessage()
      ))
    ;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<DefaultErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
    String errors = ex.getBindingResult().getFieldErrors().stream()
      .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
      .collect(Collectors.joining(", "))
    ;
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
      .body(DefaultErrorResponse.of(
        HttpStatus.BAD_REQUEST.value(),
        "Bad Request",
        errors
      ))
    ;
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<DefaultErrorResponse> handleGeneric(
    Exception ex,
    HttpServletRequest request
  ) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
      .body(DefaultErrorResponse.of(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        "Internal Server Error",
        ex.getMessage()
      ))
    ;
  }
}