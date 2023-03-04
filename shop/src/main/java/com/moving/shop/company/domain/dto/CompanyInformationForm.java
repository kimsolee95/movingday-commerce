package com.moving.shop.company.domain.dto;

import com.moving.shop.company.domain.entity.Company;
import com.moving.shop.customer.domain.type.ServiceCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyInformationForm {

  /* 가입 이메일 */
  private String email;

  /* 업체명 */
  private String name;
  
  /* 서비스카테고리 */
  private ServiceCategory serviceCategory;

  /* 업체 주소 */
  private String address;

  /* 업체 연락처 */
  private String tel;

  /* 업체 소개글 */
  private String introduction;

  public static CompanyInformationForm from(Company company) {
    return CompanyInformationForm.builder()
        .serviceCategory(company.getServiceCategory())
        .email(company.getEmail())
        .name(company.getName())
        .address(company.getAddress())
        .tel(company.getTel())
        .introduction(company.getIntroduction())
        .build();
  }
}
