package com.moving.shop.company.service;

import com.moving.shop.company.domain.dto.CompanySignUpForm;
import com.moving.shop.company.domain.entity.Company;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CompanySignUpService extends UserDetailsService {

  /* 회원가입 (업체) */
  Company signUp(CompanySignUpForm form);

}
