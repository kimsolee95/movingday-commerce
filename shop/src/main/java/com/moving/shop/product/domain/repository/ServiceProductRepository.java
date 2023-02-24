package com.moving.shop.product.domain.repository;

import com.moving.shop.product.domain.entity.ServiceProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceProductRepository extends JpaRepository<ServiceProduct, Long>, ServiceProductRepositoryCustom {

}
