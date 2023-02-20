package com.moving.shop.product.service.impl;

import com.moving.shop.common.exception.customexception.CompanyException;
import com.moving.shop.common.exception.type.CompanyErrorCode;
import com.moving.shop.common.security.TokenProvider;
import com.moving.shop.company.domain.entity.Company;
import com.moving.shop.company.domain.repository.CompanyRepository;
import com.moving.shop.product.domain.dto.AddServiceProductForm;
import com.moving.shop.product.domain.entity.ServiceProduct;
import com.moving.shop.product.domain.repository.ProductOptionRepository;
import com.moving.shop.product.domain.repository.ServiceProductRepository;
import com.moving.shop.product.service.CompanyProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyProductServiceImpl implements CompanyProductService {

  private final CompanyRepository companyRepository;
  private final ServiceProductRepository serviceProductRepository;
  private final ProductOptionRepository productOptionRepository;
  private final TokenProvider tokenProvider;

  @Override
  public ServiceProduct addServiceProduct(String refinedToken, AddServiceProductForm form) {

    String email = tokenProvider.getUsername(refinedToken);
    Company company = companyRepository.findByEmail(email)
        .orElseThrow(() -> new CompanyException(CompanyErrorCode.NOT_EXIST_COMPANY_MEMBER));

    return serviceProductRepository.save(ServiceProduct.of(company, form));
  }
}
