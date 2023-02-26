package com.moving.shop.product.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moving.shop.common.BaseEntity;
import com.moving.shop.company.domain.entity.Company;
import com.moving.shop.customer.domain.entity.CustomerRequest;
import com.moving.shop.product.domain.dto.AddServiceProductForm;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
public class ServiceProduct extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /* 업체 ID */
  @ManyToOne
  @JoinColumn(name = "company_id")
  private Company company;

  /* 서비스상품_옵션 */
  @JsonIgnore
  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "service_product_id")
  private List<ProductOption> productOptions = new ArrayList<>();

  /* 서비스상품명 */
  private String name;

  /* 상품개요설명 */
  private String outlineDescription;

  /* 상품 금액 */
  private int productPrice;

  /* 서비스 실행일시 */
  private LocalDateTime executeDate;

  /* 고객 서비스 요청서 */
//  private Long serviceRequestId;
  @ManyToOne
  @JoinColumn(name = "customer_request_id")
  private CustomerRequest customerRequest;

  /* 해당 서비스 상품 주문 여부 */
  private boolean purchaseYn;

  public static ServiceProduct of(Company company, AddServiceProductForm form, CustomerRequest customerRequest) {
    return ServiceProduct.builder()
        .company(company)
        .productOptions(
            form.getProductOptions().stream()
                .map(addProductOptionForm -> ProductOption.from(addProductOptionForm))
                .collect(Collectors.toList()))
        .name(form.getName())
        .outlineDescription(form.getOutlineDescription())
        .productPrice(form.getProductPrice())
        .executeDate(form.getExecuteDate())
//        .serviceRequestId(form.getServiceRequestId())
        .customerRequest(customerRequest)
        .purchaseYn(false)
        .build();
  }

  public static void buyServiceProduct(ServiceProduct serviceProduct) {
    serviceProduct.setPurchaseYn(true);
  }

}
