package com.moving.shop.company.domain.repository;

import com.moving.shop.company.domain.entity.Company;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {

  Optional<Company> findByEmail(String username);

  boolean existsByEmail(String email);
}
