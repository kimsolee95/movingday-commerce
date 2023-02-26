package com.moving.shop.order.domain.repository;

import com.moving.shop.order.domain.entity.ServiceOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long> {

  boolean existsByCustomerRequestId(Long customerRequestId);
}
