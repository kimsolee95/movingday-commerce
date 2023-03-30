package com.moving.shop.product.domain.repository;

import com.moving.shop.product.domain.entity.ServiceProduct;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceProductRepository extends JpaRepository<ServiceProduct, Long>, ServiceProductRepositoryCustom {

  @EntityGraph(attributePaths = {"productOptions"}, type = EntityGraphType.LOAD)
  Optional<ServiceProduct> findWithProductOptionsById(Long id);

  boolean existsById(Long id);
}
