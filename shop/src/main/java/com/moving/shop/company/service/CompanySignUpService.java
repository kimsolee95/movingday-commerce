package com.moving.shop.company.service;

import com.moving.shop.company.domain.dto.CompanySignUpForm;
import com.moving.shop.company.domain.entity.Company;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CompanySignUpService extends UserDetailsService {

  /* 회원가입 (업체) */
  Company signUp(CompanySignUpForm form);

  /* 회원 존재 여부 확인 (업체) */
  boolean existsByEmail(String email);

  /* 회원 정보 byEmail (업체) */
  Company findByEmail(String email);
}
