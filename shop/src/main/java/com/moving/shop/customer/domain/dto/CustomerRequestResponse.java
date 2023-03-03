package com.moving.shop.customer.domain.dto;

import static com.moving.shop.customer.domain.type.ServiceCategory.CLEANING;

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

  private static String readServiceCategory(ServiceCategory serviceCategory) {
    return CLEANING.equals(serviceCategory) ? "청소" : "인테리어시공";
  }

  private static String readPlaceShape(PlaceShape placeShape) {

    if (PlaceShape.APARTMENT.equals(placeShape)) {
      return "아파트";
    }

    if (PlaceShape.HOUSE.equals(placeShape)) {
      return "빌라";
    }

    if (PlaceShape.SINGLE_HOUSE.equals(placeShape)) {
      return "단독주택";
    }

    return "기타";
  }

  public static CustomerRequestResponse from(CustomerRequest customerRequest) {
    return CustomerRequestResponse.builder()
        .id(customerRequest.getId())
        .serviceAddress(customerRequest.getServiceAddress())
        .desiredDate(customerRequest.getDesiredDate())
        .detailRequest(customerRequest.getDetailRequest())
        .placeArea(customerRequest.getPlaceArea())
        .serviceCategory(readServiceCategory(customerRequest.getServiceCategory()))
        .placeShape(readPlaceShape(customerRequest.getPlaceShape()))
        .build();
  }

}
