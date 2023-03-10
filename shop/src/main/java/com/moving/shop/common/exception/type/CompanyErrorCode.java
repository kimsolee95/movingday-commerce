package com.moving.shop.common.exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CompanyErrorCode {

  NOT_MATCH_LOGIN_INFO(HttpStatus.BAD_REQUEST, "비밀번호나 이메일을 확인해주세요."),
  NOT_EXIST_COMPANY_MEMBER(HttpStatus.BAD_REQUEST, "존재하는 업체 회원이 아닙니다. 업체 회원으로 로그인 후, 서비스 이용 가능합니다."),
  SERVICE_REQUEST_NOT_EXIST(HttpStatus.BAD_REQUEST, "등록하려는 상품에 대한 고객의 서비스 요청서가 존재하지 않습니다. 고객 서비스 요청서 재확인 후, 상품 등록해주세요."),
  EMAIL_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "해당 이메일을 가진 회원이 이미 존재합니다. 다른 이메일을 사용해주세요.");

  private final HttpStatus httpStatus;
  private final String description;
}
