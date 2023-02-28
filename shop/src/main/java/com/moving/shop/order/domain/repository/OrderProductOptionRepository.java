package com.moving.shop.order.domain.repository;

import com.moving.shop.order.domain.entity.OrderProductOption;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

public interface OrderProductOptionRepository extends JpaRepository<OrderProductOption, Long> {

  List<OrderProductOption> findAllByOrderProduct_Id(@RequestParam("order_product_id") Long orderProductId);
}
