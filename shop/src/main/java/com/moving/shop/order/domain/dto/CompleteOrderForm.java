package com.moving.shop.order.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompleteOrderForm {

  /* 서비스주문 id */
  private Long serviceOrderId;

}
