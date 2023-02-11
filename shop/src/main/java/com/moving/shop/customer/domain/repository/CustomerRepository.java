package com.moving.shop.customer.domain.repository;

import com.moving.shop.customer.domain.entity.Customer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

  Optional<Customer> findByEmail(String username);

  boolean existsByEmail(String email);
}
