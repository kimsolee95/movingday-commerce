package com.moving.shop.product.service;

import com.moving.shop.product.domain.dto.AddServiceProductForm;
import com.moving.shop.product.domain.dto.CompaniesServiceProduct;
import com.moving.shop.product.domain.dto.UpdateServiceProductForm;
import com.moving.shop.product.domain.entity.ServiceProduct;
import java.util.List;

public interface CompanyProductService {

  ServiceProduct addServiceProduct(String refinedToken, AddServiceProductForm form);

  ServiceProduct updateServiceProduct(String refinedToken, UpdateServiceProductForm form);

  void deleteServiceProduct(String refinedToken, Long serviceProductId);

  List<CompaniesServiceProduct> selectNotPurchasedProduct(String refinedToken);

  List<CompaniesServiceProduct> selectPurchasedProduct(String refinedToken);
}
