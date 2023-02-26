package com.moving.shop.order.domain.entity;

import com.moving.shop.common.BaseEntity;
import com.moving.shop.customer.domain.entity.CustomerRequest;
import com.moving.shop.order.domain.type.OrderStatus;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@AllArgsConstructor
@NoArgsConstructor
@AuditOverride(forClass = BaseEntity.class)
public class ServiceOrder extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /* 서비스주문 상태 */
  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;

  /* 주문 일자 */
  private LocalDateTime orderDate;

  /* 고객 서비스요청서 ID */
  @OneToOne
  @JoinColumn(name = "customer_request_id")
  private CustomerRequest customerRequest;

  /* 서비스 주소지 */
  private String serviceAddress;

  public static ServiceOrder createServiceOrder(CustomerRequest customerRequest) {
    return ServiceOrder.builder()
        .orderStatus(OrderStatus.SUBMIT)
        .orderDate(LocalDateTime.now())
        .serviceAddress(customerRequest.getServiceAddress())
        .customerRequest(customerRequest)
        .build();
  }
}
