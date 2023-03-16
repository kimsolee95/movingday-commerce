package com.moving.shop.common.exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode {

  NOT_VALID_REQUEST_INFO(HttpStatus.BAD_REQUEST, "서비스 요청서 정보가 현재 유효하지 않아서 주문이 불가능합니다. 확인이 필요할 시, 고객센터로 주문 희망 서비스를 알려주세요."),
  NOT_MATCHED_ORDER_INFO(HttpStatus.BAD_REQUEST, "일치하지 않는 주문 정보가 있어 주문 완료 확인 처리가 불가합니다."),
  ALREADY_TAKE_ORDER(HttpStatus.BAD_REQUEST, "이미 해당 요청서에 대한 주문이 진행 중이기 때문에 중복 주문이 불가합니다."),
  NOT_VALID_PRODUCT_INFO(HttpStatus.BAD_REQUEST, "주문하려는 서비스 상품은 현재 유효하지 않아서 주문할 수 없습니다."),
  NOT_COMPLETE_ORDER(HttpStatus.BAD_REQUEST, "선택한 주문 서비스는 현재 유효하지 않아서 주문 완료 처리할 수 없습니다."),
  NOT_ENOUGH_CASH(HttpStatus.BAD_REQUEST, "현재 잔액 캐쉬로 서비스 주문이 불가합니다. 캐쉬를 충전해주세요.");

  private final HttpStatus httpStatus;
  private final String description;
}
