package com.moving.shop.product.domain.dto;

import com.moving.shop.company.domain.dto.CompanyInformationForm;
import com.moving.shop.company.domain.entity.Company;
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
public class ProposedProductResponse {

  /* 서비스상품 id */
  private Long id;

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

  private CompanyInformationForm companyInfo;

  public static List<ProposedProductResponse> from(List<ServiceProduct> serviceProducts) {

    return serviceProducts.stream().map(serviceProduct -> from(serviceProduct))
        .collect(Collectors.toList());
  }

  private static ProposedProductResponse from(ServiceProduct serviceProduct) {
    return ProposedProductResponse.builder()
        .id(serviceProduct.getId())
        .name(serviceProduct.getName())
        .outlineDescription(serviceProduct.getOutlineDescription())
        .productPrice(serviceProduct.getProductPrice())
        .executeDate(serviceProduct.getExecuteDate())
        .companyInfo(CompanyInformationForm.from(serviceProduct.getCompany()))
        .build();
  }
}
