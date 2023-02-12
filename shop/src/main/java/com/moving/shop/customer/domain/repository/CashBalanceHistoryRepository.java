package com.moving.shop.customer.domain.repository;

import com.moving.shop.customer.domain.entity.CashBalanceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashBalanceHistoryRepository extends JpaRepository<CashBalanceHistory, Long> {

}
