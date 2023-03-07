package com.moving.shop.order.service.impl;

import com.moving.shop.common.exception.customexception.CompanyException;
import com.moving.shop.common.exception.type.CompanyErrorCode;
import com.moving.shop.common.security.TokenProvider;
import com.moving.shop.company.domain.entity.Company;
import com.moving.shop.company.domain.repository.CompanyRepository;
import com.moving.shop.order.domain.dto.SubmittedOrders;
import com.moving.shop.order.domain.repository.OrderProductRepository;
import com.moving.shop.order.service.CompanyOrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyOrderServiceImpl implements CompanyOrderService {

  private final OrderProductRepository orderProductRepository;
  private final CompanyRepository companyRepository;
  private final TokenProvider tokenProvider;

  @Override
  public List<SubmittedOrders> findSubmittedOrderProductsByCompanyId(String refinedToken) {

    String email = tokenProvider.getUsername(refinedToken);
    Company company = companyRepository.findByEmail(email)
        .orElseThrow(() -> new CompanyException(CompanyErrorCode.NOT_EXIST_COMPANY_MEMBER));

    return orderProductRepository.findByCompanyId(company.getId());
  }
}
