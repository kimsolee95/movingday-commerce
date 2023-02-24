package com.moving.shop.product.service.impl;

import com.moving.shop.common.exception.customexception.CompanyException;
import com.moving.shop.common.exception.type.CompanyErrorCode;
import com.moving.shop.common.security.TokenProvider;
import com.moving.shop.company.domain.entity.Company;
import com.moving.shop.company.domain.repository.CompanyRepository;
import com.moving.shop.customer.domain.entity.CustomerRequest;
import com.moving.shop.customer.domain.repository.CustomerRequestRepository;
import com.moving.shop.product.domain.dto.AddServiceProductForm;
import com.moving.shop.product.domain.entity.ServiceProduct;
import com.moving.shop.product.domain.repository.ServiceProductRepository;
import com.moving.shop.product.service.CompanyProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyProductServiceImpl implements CompanyProductService {

  private final CompanyRepository companyRepository;
  private final ServiceProductRepository serviceProductRepository;
  private final CustomerRequestRepository customerRequestRepository;
  private final TokenProvider tokenProvider;

  @Override
  public ServiceProduct addServiceProduct(String refinedToken, AddServiceProductForm form) {

    String email = tokenProvider.getUsername(refinedToken);
    Company company = companyRepository.findByEmail(email)
        .orElseThrow(() -> new CompanyException(CompanyErrorCode.NOT_EXIST_COMPANY_MEMBER));

    //상품 등록 시에 참조할 고객요청서가 필요한 것으로 기획했기에 고객서비스요청서ID 존재여부 확인
//    boolean isValidRequest = customerRequestRepository.existsById(form.getServiceRequestId());
//    if (!isValidRequest) {
//      throw new CompanyException(CompanyErrorCode.SERVICE_REQUEST_NOT_EXIST);
//    }
    //erd 수정으로 인한 코드 수정 (연관관계 수정)
    CustomerRequest customerRequest = customerRequestRepository.findById(form.getServiceRequestId())
        .orElseThrow(() -> new CompanyException(CompanyErrorCode.SERVICE_REQUEST_NOT_EXIST));

    return serviceProductRepository.save(ServiceProduct.of(company, form, customerRequest));
  }
}
