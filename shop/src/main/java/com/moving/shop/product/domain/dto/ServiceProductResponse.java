package com.moving.shop.product.domain.dto;

import com.moving.shop.product.domain.entity.ServiceProduct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceProductResponse {

  /* 서비스상품_옵션 */
  private List<ProductOptionResponse> productOptionResponses;

  /* 서비스상품명 */
  private String name;

  /* 상품개요설명 */
  private String outlineDescription;

  /* 상품 금액 */
  private int productPrice;

  /* 서비스 실행일시 */
  private LocalDateTime executeDate;

  /* 고객 서비스 요청서 ID */
  private Long serviceRequestId;

  public static ServiceProductResponse from(ServiceProduct serviceProduct) {
    return ServiceProductResponse.builder()
        .productOptionResponses(serviceProduct.getProductOptions().stream()
            .map(productOption -> ProductOptionResponse.from(productOption))
            .collect(Collectors.toList()))
        .name(serviceProduct.getName())
        .outlineDescription(serviceProduct.getOutlineDescription())
        .productPrice(serviceProduct.getProductPrice())
        .executeDate(serviceProduct.getExecuteDate())
        .serviceRequestId(serviceProduct.getServiceRequestId())
        .build();
  }

}
