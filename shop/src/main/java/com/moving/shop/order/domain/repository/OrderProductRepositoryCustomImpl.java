package com.moving.shop.order.domain.repository;

import com.moving.shop.order.domain.dto.QSubmittedOrders;
import com.moving.shop.order.domain.dto.SubmittedOrders;
import com.moving.shop.order.domain.entity.QOrderProduct;
import com.moving.shop.order.domain.entity.QServiceOrder;
import com.moving.shop.order.domain.type.OrderStatus;
import com.moving.shop.product.domain.entity.QServiceProduct;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderProductRepositoryCustomImpl implements OrderProductRepositoryCustom {


  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<SubmittedOrders> findByCompanyId(Long companyId) {

    QServiceOrder serviceOrder = QServiceOrder.serviceOrder;
    QOrderProduct orderProduct = QOrderProduct.orderProduct;
    QServiceProduct serviceProduct = QServiceProduct.serviceProduct;

    List<SubmittedOrders> orderProducts = jpaQueryFactory
        .select(new QSubmittedOrders(orderProduct.id, orderProduct.orderPrice, orderProduct.executeDate, orderProduct.serviceOrder.id,
            serviceProduct.name))
        .from(orderProduct)
        .join(orderProduct.serviceProduct, serviceProduct)
        .on(orderProduct.serviceProduct.company.id.eq(companyId)) //where
        //.fetchJoin()
        .join(orderProduct.serviceOrder, serviceOrder)
        .on(serviceOrder.orderStatus.eq(OrderStatus.valueOf("SUBMIT"))) //where
        .fetch();
    return orderProducts;
  }
}
