package com.moving.shop.company.service;

import com.moving.shop.company.domain.dto.CompanyInfo;

public interface CompanyService {

  CompanyInfo getCompanyInfo(String refinedToken);

}
