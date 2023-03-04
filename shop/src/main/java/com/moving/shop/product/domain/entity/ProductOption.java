package com.moving.shop.product.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.moving.shop.common.BaseEntity;
import com.moving.shop.product.domain.dto.AddProductOptionForm;
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
public class ProductOption extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /* 서비스상품 ID */
  @JsonBackReference //양방향 매핑 시 순환참조 방지
  @ManyToOne
  @JoinColumn(name = "service_product_id")
  private ServiceProduct serviceProduct;

  /* 상품옵션명 */
  private String name;

  /* 상품옵션 가격 */
  private int optionPrice;

  /* 상품옵션 주문여부 */
  private boolean purchaseYn;

  public static ProductOption from(AddProductOptionForm form) {
    return ProductOption.builder()
        .name(form.getName())
        .optionPrice(form.getOptionPrice())
        .purchaseYn(false)
        .build();
  }

  public static void buyOption(ProductOption productOption) {
    productOption.setPurchaseYn(true);
  }

}
