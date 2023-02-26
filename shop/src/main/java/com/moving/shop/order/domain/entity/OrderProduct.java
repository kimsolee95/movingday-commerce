package com.moving.shop.order.domain.entity;

import com.moving.shop.common.BaseEntity;
import com.moving.shop.order.domain.dto.AddOrderProductForm;
import com.moving.shop.product.domain.entity.ProductOption;
import com.moving.shop.product.domain.entity.ServiceProduct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
public class OrderProduct extends BaseEntity {

  /* 주문_서비스상품 ID */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /* 서비스상품 주문금액 */
  private int orderPrice;

  /* 서비스 실행일시 */
  private LocalDateTime executeDate;

  /* 주문서비스상품옵션 */
  @OneToMany(cascade = CascadeType.ALL) //mappedBy = "orderProduct",
  @JoinColumn(name = "order_product_id")
  private List<OrderProductOption> orderProductOptions = new ArrayList<>();

  /* 서비스상품 ID */
  @OneToOne
  @JoinColumn(name = "service_product_id")
  private ServiceProduct serviceProduct;

  /* 주문 ID */
  @OneToOne
  @JoinColumn(name = "service_order_id")
  private ServiceOrder serviceOrder;

  public static OrderProduct of(ServiceOrder serviceOrder, ServiceProduct serviceProduct,
      AddOrderProductForm form, List<ProductOption> wishOptions) {

    return OrderProduct.builder()
        .orderPrice(form.getProductPrice())
        .executeDate(serviceProduct.getExecuteDate())
        .orderProductOptions(
            wishOptions.stream().map(option -> OrderProductOption.from(option))
                .collect(Collectors.toList()))
        .serviceProduct(serviceProduct)
        .serviceOrder(serviceOrder)
        .build();
  }

}
