package com.moving.shop.customer.domain.entity;

import com.moving.shop.common.BaseEntity;
import com.moving.shop.customer.domain.type.PlaceShape;
import com.moving.shop.customer.domain.type.ServiceCategory;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
public class CustomerRequest extends BaseEntity {

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(targetEntity = Customer.class, fetch = FetchType.LAZY)
  private Customer customer;

  /* 서비스 주소지 */
  private String serviceAddress;

  /* 서비스 시행 요청일 */
  private LocalDate desiredDate;

  /* 상세요청사항 */
  private String detailRequest;

  /* 장소면적 (평) */
  private Integer placeArea;

  /* 서비스 카테고리 */
  @Enumerated(EnumType.STRING)
  private ServiceCategory serviceCategory;

  /* 서비스 장소 구조 */
  @Enumerated(EnumType.STRING)
  private PlaceShape placeShape;

}
