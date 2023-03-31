package com.moving.shop.product.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class CompaniesServiceProduct {

  /* 서비스상품_ID */
  private Long id;

  /* 서비스상품명 */
  private String name;

  /* 서비스상품 개요 */
  private String outlineDescription;

  /* 서비스상품 가격 */
  private int productPrice;

  /* 서비스상품 고객 구매 여부 */
  private boolean purchaseYn;

  @QueryProjection
  public CompaniesServiceProduct(Long id, String name, String outlineDescription, int productPrice, boolean purchaseYn) {
    this.id = id;
    this.name = name;
    this.outlineDescription = outlineDescription;
    this.productPrice = productPrice;
    this.purchaseYn = purchaseYn;
  }

}
