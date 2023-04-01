package com.moving.shop.product.domain.repository;

import com.moving.shop.company.domain.entity.QCompany;
import com.moving.shop.customer.domain.entity.QCustomerRequest;
import com.moving.shop.order.domain.entity.QOrderProduct;
import com.moving.shop.product.domain.dto.CompaniesServiceProduct;
import com.moving.shop.product.domain.dto.QCompaniesServiceProduct;
import com.moving.shop.product.domain.entity.QProductOption;
import com.moving.shop.product.domain.entity.QServiceProduct;
import com.moving.shop.product.domain.entity.ServiceProduct;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ServiceProductRepositoryCustomImpl implements ServiceProductRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<ServiceProduct> findByCustomerId(Long customerId) {

    QServiceProduct serviceProduct = QServiceProduct.serviceProduct;
    QCustomerRequest customerRequest = QCustomerRequest.customerRequest;

    return jpaQueryFactory.selectFrom(serviceProduct)
        .innerJoin(serviceProduct.customerRequest, customerRequest).fetchJoin()
        .where(customerRequest.customer.id.eq(customerId))
        .fetch();
  }

  @Override
  public List<CompaniesServiceProduct> findAllByCompanyIdAndPurchaseYnFalse(Long companyId) {

    QServiceProduct serviceProduct = QServiceProduct.serviceProduct;
    QOrderProduct orderProduct = QOrderProduct.orderProduct;

    List<CompaniesServiceProduct> companiesServiceProducts = jpaQueryFactory
        .select(new QCompaniesServiceProduct(serviceProduct.id, serviceProduct.name,
            serviceProduct.outlineDescription, serviceProduct.productPrice,
            serviceProduct.purchaseYn))
        .from(serviceProduct)
        .where(serviceProduct.company.id.eq(companyId)
          .and(serviceProduct.purchaseYn.eq(false))
          .and(JPAExpressions.selectOne()
            .from(orderProduct)
            .where(orderProduct.serviceProduct.id.eq(companyId))
            .notExists()))
        .fetch();

    return companiesServiceProducts;
  }

}
