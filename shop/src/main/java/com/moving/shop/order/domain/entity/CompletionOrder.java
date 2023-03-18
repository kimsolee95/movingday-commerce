package com.moving.shop.order.domain.entity;

import com.moving.shop.common.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
public class CompletionOrder extends BaseEntity {

  /* 주문_서비스상품 ID */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "service_order_id")
  private ServiceOrder serviceOrder;

  private int orderPrice;

  /* 고객 확인 여부 */
  private boolean customerCheckYn;

  /* 업체 확인 여부 */
  private boolean companyCheckYn;

  /* 주문완료 고객 확인 시 사용 verifycationCode */
  private String verificationCode;

  public void customersVerify() {
    this.customerCheckYn = true;
  }
}
