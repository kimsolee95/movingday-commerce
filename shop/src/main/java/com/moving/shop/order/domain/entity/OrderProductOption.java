package com.moving.shop.order.domain.entity;

import com.moving.shop.common.BaseEntity;
import com.moving.shop.order.domain.dto.AddOrderProductOptionForm;
import com.moving.shop.product.domain.entity.ProductOption;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
public class OrderProductOption extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /* 주문서비스상품 ID */
  @ManyToOne
  @JoinColumn(name = "order_product_id")
  private OrderProduct orderProduct;

  /* 상품옵션명 */
  private String name;

  /* 상품옵션 가격 */
  private int optionPrice;

  public static OrderProductOption from(ProductOption productOption) {
    return OrderProductOption.builder()
        .name(productOption.getName())
        .optionPrice(productOption.getOptionPrice())
        .build();
  }
}
