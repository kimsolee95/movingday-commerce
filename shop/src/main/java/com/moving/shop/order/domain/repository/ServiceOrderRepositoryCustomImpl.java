package com.moving.shop.order.domain.repository;

import com.moving.shop.customer.domain.entity.QCustomer;
import com.moving.shop.customer.domain.entity.QCustomerRequest;
import com.moving.shop.order.domain.dto.CustomerInfoByServiceOrder;
import com.moving.shop.order.domain.dto.QCustomerInfoByServiceOrder;
import com.moving.shop.order.domain.entity.QServiceOrder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ServiceOrderRepositoryCustomImpl implements ServiceOrderRepositoryCustom{

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public CustomerInfoByServiceOrder getCustomerInfoByServiceOrderId(Long serviceOrderId) {

    QServiceOrder serviceOrder = QServiceOrder.serviceOrder;
    QCustomerRequest customerRequest = QCustomerRequest.customerRequest;
    QCustomer customer = QCustomer.customer;

    CustomerInfoByServiceOrder customerInfoByServiceOrder = jpaQueryFactory
        .select(new QCustomerInfoByServiceOrder(customer.id, customer.phone))
        .from(serviceOrder)
        .join(serviceOrder.customerRequest, customerRequest)
        .join(customerRequest.customer, customer)
        .on(serviceOrder.id.eq(serviceOrderId))
        .fetchFirst();
    return customerInfoByServiceOrder;
  }
}
