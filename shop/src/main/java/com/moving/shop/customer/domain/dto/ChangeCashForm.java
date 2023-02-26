package com.moving.shop.customer.domain.dto;

import javax.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeCashForm {

  private String fromWhom;

  private String description;

  @Max(value = 1000000, message = "변경금액은 1,000,000원 이하의 액수만 가능합니다.")
  private Integer cash;
}
