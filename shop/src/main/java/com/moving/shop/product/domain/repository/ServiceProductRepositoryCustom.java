package com.moving.shop.product.domain.repository;

import com.moving.shop.product.domain.entity.ServiceProduct;
import java.util.List;

public interface ServiceProductRepositoryCustom {

  List<ServiceProduct> findByCustomerId(Long customerId);

}
