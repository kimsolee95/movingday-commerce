package com.moving.shop.customer.domain.repository;

import com.moving.shop.customer.domain.entity.CashBalanceHistory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

public interface CashBalanceHistoryRepository extends JpaRepository<CashBalanceHistory, Long> {

  Optional<CashBalanceHistory> findFirstByCustomer_IdOrderByIdDesc(@RequestParam("customer_id") Long customerId);
}
