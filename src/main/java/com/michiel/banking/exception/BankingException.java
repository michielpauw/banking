package com.michiel.banking.exception;

public class BankingException extends Exception {

  private final ErrorCode errorCode;

  public BankingException(final ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  public BankingException(final ErrorCode errorCode, final String message) {
    super(errorCode.getMessage() + " | " + message);
    this.errorCode = errorCode;
  }
}
