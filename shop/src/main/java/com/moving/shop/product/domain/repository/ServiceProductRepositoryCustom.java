package com.moving.shop.product.domain.repository;

import com.moving.shop.product.domain.entity.ServiceProduct;
import java.util.List;

public interface ServiceProductRepositoryCustom {

  List<ServiceProduct> findByCustomerId(Long customerId);

  // n + 1 쿼리 나가는 문제 발생
  //List<ServiceProduct> findWithProductOptionsById(Long serviceProductId);

}
