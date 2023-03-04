package com.moving.shop.product.domain.repository;

import com.moving.shop.company.domain.entity.QCompany;
import com.moving.shop.customer.domain.entity.QCustomerRequest;
import com.moving.shop.product.domain.entity.QProductOption;
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

  //fetch join으로 해결할 것이 아니라 return 용 dto를 만들어서 하는 법으로 수정 필요.
//  @Override
//  public List<ServiceProduct> findWithProductOptionsById(Long serviceProductId) {
//
//    QServiceProduct serviceProduct = QServiceProduct.serviceProduct;
//    QProductOption productOption = QProductOption.productOption;
//    QCompany company = QCompany.company;
//    QCustomerRequest customerRequest = QCustomerRequest.customerRequest;
//
//    return jpaQueryFactory.selectFrom(serviceProduct)
//        .join(serviceProduct.productOptions, productOption).fetchJoin()
//        .join(serviceProduct.company, company).fetchJoin()
//        .join(serviceProduct.customerRequest, customerRequest).fetchJoin()
//        .where(serviceProduct.id.eq(serviceProductId))
//        .fetch();
//  }
}
