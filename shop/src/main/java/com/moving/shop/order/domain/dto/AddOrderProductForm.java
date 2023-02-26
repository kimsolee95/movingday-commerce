package com.moving.shop.order.domain.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddOrderProductForm {

  /* 주문 처리하려는 고객 서비스요청서 ID */
  private Long customerRequestId;

  /* 주문하려는 서비스상품 ID */
  private Long serviceProductId;

  /* 주문하려는 서비스상품 금액 */
  private int productPrice;

  /* 주문하려는 서비스상품 옵션목록 */
  private List<AddOrderProductOptionForm> addOrderProductOptionForms;
}
