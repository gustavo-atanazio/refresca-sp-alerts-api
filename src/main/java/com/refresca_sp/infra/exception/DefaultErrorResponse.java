package com.refresca_sp.infra.exception;

import java.time.LocalDateTime;

public record DefaultErrorResponse(
  String timestamp,
  int status,
  String error,
  String message
) {

  public static DefaultErrorResponse of(int status, String error, String message) {
    return new DefaultErrorResponse(
      LocalDateTime.now().toString(),
      status,
      error,
      message
    );
  }
}