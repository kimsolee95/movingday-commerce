package com.moving.shop.company.service.impl;

import com.moving.shop.common.exception.customexception.CompanyException;
import com.moving.shop.common.exception.type.CompanyErrorCode;
import com.moving.shop.common.security.TokenProvider;
import com.moving.shop.company.domain.dto.CompanyInfo;
import com.moving.shop.company.domain.entity.Company;
import com.moving.shop.company.domain.repository.CompanyRepository;
import com.moving.shop.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

  private final CompanyRepository companyRepository;
  private final TokenProvider tokenProvider;

  @Override
  public CompanyInfo getCompanyInfo(String refinedToken) {

    String email = tokenProvider.getUsername(refinedToken);
    Company company = companyRepository.findByEmail(email)
        .orElseThrow(() -> new CompanyException(CompanyErrorCode.NOT_EXIST_COMPANY_MEMBER));

    return CompanyInfo.builder()
        .email(company.getEmail())
        .name(company.getName())
        .tel(company.getTel())
        .build();
  }
}
