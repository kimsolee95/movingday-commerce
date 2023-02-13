package com.moving.shop.customer.domain.repository;

import com.moving.shop.customer.domain.entity.CustomerRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRequestRepository extends JpaRepository<CustomerRequest, Long> {

}
