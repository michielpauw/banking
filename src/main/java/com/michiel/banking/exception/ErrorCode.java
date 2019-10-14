package com.michiel.banking.exception;

public enum ErrorCode {

  GENERIC_ERROR("GENERIC_ERROR", "Contact System Administrator."),
  INVALID_REQUEST("INVALID_REQUEST", "Request format is incorrect.");


  private final String code;
  private final String message;

  private ErrorCode(final String code, final String message) {
    this.code = code;
    this.message = message;
  }

  public String getMessage() { return this.message; }
}
