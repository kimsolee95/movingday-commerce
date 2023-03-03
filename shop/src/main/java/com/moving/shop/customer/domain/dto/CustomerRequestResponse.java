package com.moving.shop.customer.domain.dto;

import com.moving.shop.customer.domain.entity.CustomerRequest;
import com.moving.shop.customer.domain.type.PlaceShape;
import com.moving.shop.customer.domain.type.ServiceCategory;
import java.time.LocalDate;
import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestResponse {

  private Long id;

  /* 서비스 주소지 */
  private String serviceAddress;

  /* 서비스 시행 요청일 */
  private LocalDate desiredDate;

  /* 상세요청사항 */
  private String detailRequest;

  /* 장소면적 (평) */
  private Integer placeArea;

  /* 서비스 카테고리 */
  private String serviceCategory;

  /* 서비스 장소 구조 */
  private String placeShape;

  public static CustomerRequestResponse from(CustomerRequest customerRequest) {
    return CustomerRequestResponse.builder()
        .id(customerRequest.getId())
        .serviceAddress(customerRequest.getServiceAddress())
        .desiredDate(customerRequest.getDesiredDate())
        .detailRequest(customerRequest.getDetailRequest())
        .placeArea(customerRequest.getPlaceArea())
        .serviceCategory(String.valueOf(customerRequest.getServiceCategory()))
        .placeShape(String.valueOf(customerRequest.getPlaceShape()))
        .build();
  }

}
