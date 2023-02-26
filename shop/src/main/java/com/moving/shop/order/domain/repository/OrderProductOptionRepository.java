package com.moving.shop.order.domain.repository;

import com.moving.shop.order.domain.entity.OrderProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductOptionRepository extends JpaRepository<OrderProductOption, Long> {

}
