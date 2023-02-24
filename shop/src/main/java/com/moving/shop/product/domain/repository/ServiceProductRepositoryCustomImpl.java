package com.moving.shop.product.domain.repository;

import com.moving.shop.customer.domain.entity.QCustomerRequest;
import com.moving.shop.product.domain.entity.QServiceProduct;
import com.moving.shop.product.domain.entity.ServiceProduct;
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
}
