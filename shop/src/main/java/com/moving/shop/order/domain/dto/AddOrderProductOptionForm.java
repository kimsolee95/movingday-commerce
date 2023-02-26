package com.moving.shop.order.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddOrderProductOptionForm {

  /* 주문하려는 상품옵션 ID */
  private Long productOptionId;

  /* 상품옵션 가격 */
  private int optionPrice;
}
