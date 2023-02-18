package com.moving.shop.common.exception.customexception;

import com.moving.shop.common.exception.type.CompanyErrorCode;
import lombok.Getter;
/*
 * 업체회원 업무 - custom exception
 * */
@Getter
public class CompanyException extends RuntimeException {

  private int status;
  private CompanyErrorCode companyErrorCode;

  public CompanyException(CompanyErrorCode companyErrorCode) {
    this.status = companyErrorCode.getHttpStatus().value();
    this.companyErrorCode = companyErrorCode;
  }
}
