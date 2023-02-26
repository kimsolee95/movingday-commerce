package com.moving.shop.common.exception.customexception;

import com.moving.shop.common.exception.type.OrderErrorCode;
import lombok.Getter;

/*
 * 서비스 주문 업무 - custom exception
 * */
@Getter
public class OrderException extends RuntimeException {

  private int status;
  private OrderErrorCode orderErrorCode;

  public OrderException(OrderErrorCode orderErrorCode) {
    this.status = orderErrorCode.getHttpStatus().value();
    this.orderErrorCode = orderErrorCode;
  }
}
