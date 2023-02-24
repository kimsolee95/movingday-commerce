package com.moving.shop.product.service;

import com.moving.shop.product.domain.entity.ServiceProduct;
import java.util.List;

public interface CustomerProductService {

  List<ServiceProduct> findByCustomerId(String refinedToken);
}
