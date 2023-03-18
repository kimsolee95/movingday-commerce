package com.moving.shop.order.domain.repository;

import com.moving.shop.order.domain.entity.CompletionOrder;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompletionOrderRepository extends JpaRepository<CompletionOrder, Long> {

  Optional<CompletionOrder> findByIdAndVerificationCode(Long completionOrderId, String code);
}
