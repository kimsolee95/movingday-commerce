package com.moving.shop.order.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class SubmittedOrders {

  /* 주문_서비스상품 ID */
  private Long orderProductId;

  /* 서비스상품 주문금액 */
  private int orderPrice;

  /* 서비스 실행일시 */
  private LocalDateTime executeDate;

  /* 주문 ID */
  private Long serviceOrderId;

  @QueryProjection
  public SubmittedOrders(Long orderProductId, int orderPrice, LocalDateTime executeDate, Long serviceOrderId) {
    this.orderProductId = orderProductId;
    this.orderPrice = orderPrice;
    this.executeDate = executeDate;
    this.serviceOrderId = serviceOrderId;
  }

}
