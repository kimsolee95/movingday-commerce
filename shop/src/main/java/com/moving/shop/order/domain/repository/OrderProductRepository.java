package com.moving.shop.order.domain.repository;

import com.moving.shop.order.domain.entity.OrderProduct;
import java.lang.StackWalker.Option;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long>, OrderProductRepositoryCustom {

  @EntityGraph(attributePaths = {"orderProductOptions"}, type = EntityGraphType.LOAD)
  Optional<OrderProduct> findWithOrderProductOptionByServiceOrder_Id(@RequestParam("service_order_id") Long serviceOrderId);

  Optional<OrderProduct> findByServiceOrder_Id(@RequestParam("service_order_id") Long serviceOrderId);
}
