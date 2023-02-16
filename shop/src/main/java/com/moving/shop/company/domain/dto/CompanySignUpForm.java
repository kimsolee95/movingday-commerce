package com.moving.shop.company.domain.dto;

import com.moving.shop.customer.domain.type.ServiceCategory;
import java.util.Locale;
import lombok.Getter;

@Getter
public class CompanySignUpForm {

  /* 서비스 카테고리 */
  private String serviceCategory;

  /* 가입 이메일 */
  private String email;

  /* 비밀번호 */
  private String password;

  /* 업체 주소 */
  private String address;

  /* 업체 상세 주소 */
  private String addressDetail;

  /* 업체우편번호 */
  private String zipcode;

  /* 업체 연락처 */
  private String tel;

  /* 업체 소개글 */
  private String introduction;

  /* 사업자번호 */
  private String businessNumber;

  /* 서비스카테고리 ENUM TYPE 반환 */
  public ServiceCategory getServiceCategoryType() {

    if (this.serviceCategory.trim().isEmpty()) {
      return null;
    }
    return ServiceCategory.valueOf(this.serviceCategory.toUpperCase(Locale.ROOT));
  }

}
