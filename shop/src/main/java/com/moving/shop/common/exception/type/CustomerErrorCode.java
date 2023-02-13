package com.moving.shop.common.exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomerErrorCode {

  NOT_EXIST_MEMBER(HttpStatus.BAD_REQUEST, "등록되지 않은 계정입니다."),
  EMAIL_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "해당 이메일을 가진 회원이 이미 존재합니다. 다른 이메일을 사용해주세요."),
  NOT_MATCH_LOGIN_INFO(HttpStatus.BAD_REQUEST, "비밀번호나 이메일을 확인해주세요."),
  SHORT_OF_BALANCE(HttpStatus.BAD_REQUEST, "캐시 잔액이 모자릅니다. 캐시를 충전 후 서비스 진행해주세요."),
  NOT_CORRECT_INPUT(HttpStatus.BAD_REQUEST, "잘못된 값을 입력하였습니다.");

  private final HttpStatus httpStatus;
  private final String description;
}
