package com.moving.shop.product.domain.dto;

import com.moving.shop.product.domain.entity.ProductOption;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionResponse {

  /* 상품옵션명 */
  private String name;

  /* 상품옵션 가격 */
  private int optionPrice;

  /* 상품옵션 주문여부 */
  private boolean purchaseYn;

  public static ProductOptionResponse from(ProductOption productOption) {
    return ProductOptionResponse.builder()
        .name(productOption.getName())
        .optionPrice(productOption.getOptionPrice())
        .purchaseYn(productOption.isPurchaseYn())
        .build();
  }

}
