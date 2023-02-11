package com.moving.shop.common.exception.customexception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moving.shop.common.exception.type.CustomerErrorCode;
import lombok.Getter;

/*
* 고객회원 업무 - custom exception
* */
@Getter
public class CustomerException extends RuntimeException{

  private int status;
  private CustomerErrorCode customerErrorCode;
  private static final ObjectMapper mapper = new ObjectMapper();

  public CustomerException(CustomerErrorCode customerErrorCode) {
    this.status = customerErrorCode.getHttpStatus().value();
    this.customerErrorCode = customerErrorCode;
  }
}
