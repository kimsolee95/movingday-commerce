package com.moving.shop.order.domain.repository;

import com.moving.shop.order.domain.entity.CompletionOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompletionOrderRepository extends JpaRepository<CompletionOrder, Long> {

}
