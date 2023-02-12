package com.moving.shop.common.exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomerErrorCode {

  EMAIL_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "해당 이메일을 가진 회원이 이미 존재합니다. 다른 이메일을 사용해주세요."),
  NOT_MATCH_LOGIN_INFO(HttpStatus.BAD_REQUEST, "비밀번호나 이메일을 확인해주세요.");

  private final HttpStatus httpStatus;
  private final String description;
}
