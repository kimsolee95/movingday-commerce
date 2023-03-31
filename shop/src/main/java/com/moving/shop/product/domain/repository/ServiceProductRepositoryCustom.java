package com.moving.shop.product.domain.repository;

import com.moving.shop.product.domain.dto.CompaniesServiceProduct;
import com.moving.shop.product.domain.entity.ServiceProduct;
import java.util.List;

public interface ServiceProductRepositoryCustom {

  List<ServiceProduct> findByCustomerId(Long customerId);

  List<CompaniesServiceProduct> findAllByCompanyId(Long companyId);
}
