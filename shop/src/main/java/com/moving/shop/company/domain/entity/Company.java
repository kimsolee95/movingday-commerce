package com.moving.shop.company.domain.entity;

import com.moving.shop.common.BaseEntity;
import com.moving.shop.company.domain.dto.CompanySignUpForm;
import com.moving.shop.customer.domain.type.MemberType;
import com.moving.shop.customer.domain.type.ServiceCategory;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditOverride;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AuditOverride(forClass = BaseEntity.class)
public class Company extends BaseEntity {

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /* 서비스 카테고리 */
  @Enumerated(EnumType.STRING)
  private ServiceCategory serviceCategory;
  
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

  /* 업체 승인여부 */
  private boolean approvalYn;

  /* 사업자번호 */
  private String businessNumber;

  /* 회원유형 */
  @Enumerated(EnumType.STRING)
  private MemberType memberType;

  public static Company of(CompanySignUpForm form, String encodedPassword) {
    return Company.builder()
        .serviceCategory(form.getServiceCategoryType())
        .email(form.getEmail())
        .password(encodedPassword)
        .address(form.getAddress())
        .addressDetail(form.getAddressDetail())
        .zipcode(form.getZipcode())
        .tel(form.getTel())
        .introduction(form.getIntroduction())
        .approvalYn(true)
        .businessNumber(form.getBusinessNumber())
        .memberType(MemberType.COMPANY)
        .build();
  }

}
