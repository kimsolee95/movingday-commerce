package com.moving.shop.product.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddProductOptionForm {

  /* 서비스상품ID */
  private Long serviceProductId;

  /* 상품옵션명 */
  private String name;

  /* 상품옵션 가격 */
  private int optionPrice;
}
