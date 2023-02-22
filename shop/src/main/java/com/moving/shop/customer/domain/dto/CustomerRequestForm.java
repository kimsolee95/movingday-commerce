package com.moving.shop.customer.domain.dto;

import com.moving.shop.common.exception.customexception.CustomerException;
import com.moving.shop.common.exception.type.CustomerErrorCode;
import com.moving.shop.customer.domain.type.PlaceShape;
import com.moving.shop.customer.domain.type.ServiceCategory;
import java.time.LocalDate;
import java.util.Locale;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestForm {

  /* 서비스 주소지 */
  @NotBlank(message = "서비스를 진행하실 장소의 주소를 입력해주세요.")
  private String serviceAddress;

  /* 서비스 시행 요청일 */
  @Future(message = "오늘 이후의 날짜를 설정해주세요.")
  private LocalDate desiredDate;

  /* 상세요청사항 */
  private String detailRequest;

  /* 장소면적 (평) */
  @Positive(message = "입력하신 평수를 확인해주세요.")
  private Integer placeArea;

  /* 서비스 카테고리 */
  @NotBlank(message = "서비스 카테고리를 설정해주세요.")
  private String serviceCategory;

  /* 서비스 장소 구조 */
  @NotBlank(message = "서비스를 진행하실 장소 구조를 입력해주세요.")
  private String placeShape;

  /* 서비스카테고리 ENUM TYPE 반환 */
  public ServiceCategory getServiceCategoryType() {

    if (this.serviceCategory.trim().isEmpty()) {
      return null;
    }
      return ServiceCategory.valueOf(this.serviceCategory.toUpperCase(Locale.ROOT));
  }

  /* 서비스장소 구조 ENUM TYPE 반환 */
  public PlaceShape getPlaceShapeType() {

    if (this.placeShape.trim().isEmpty()) {
      return null;
    }
      return PlaceShape.valueOf(this.placeShape.toUpperCase(Locale.ROOT));
  }

}
