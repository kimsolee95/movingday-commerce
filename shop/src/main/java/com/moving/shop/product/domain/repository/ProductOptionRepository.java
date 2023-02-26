package com.moving.shop.product.domain.repository;

import com.moving.shop.product.domain.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

  boolean existsById(Long id);
}
