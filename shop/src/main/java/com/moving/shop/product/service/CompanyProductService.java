package com.moving.shop.product.service;

import com.moving.shop.product.domain.dto.AddServiceProductForm;
import com.moving.shop.product.domain.entity.ServiceProduct;

public interface CompanyProductService {

  ServiceProduct addServiceProduct(String refinedToken, AddServiceProductForm form);
}
