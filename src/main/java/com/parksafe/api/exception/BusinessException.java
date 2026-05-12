package com.parksafe.api.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

  private final String code;

  private final HttpStatus httpStatus;

  public BusinessException(String code, String message, HttpStatus httpStatus) {
    super(message);
    this.code = code;
    this.httpStatus = httpStatus;

  }

}
