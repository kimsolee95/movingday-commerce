package com.moving.shop.company.application;

import com.moving.shop.common.exception.customexception.CompanyException;
import com.moving.shop.common.exception.type.CompanyErrorCode;
import com.moving.shop.common.security.TokenProvider;
import com.moving.shop.company.domain.dto.CompanySignInForm;
import com.moving.shop.company.domain.entity.Company;
import com.moving.shop.company.service.CompanySignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanySignInApplication {

  private final TokenProvider tokenProvider;
  private final PasswordEncoder passwordEncoder;
  private final CompanySignUpService companySignUpService;

  public String generateToken(CompanySignInForm form) {

    Company company = companySignUpService.findByEmail(form.getEmail());

    if (!this.passwordEncoder.matches(form.getPassword(), company.getPassword())) {
      throw new CompanyException(CompanyErrorCode.NOT_MATCH_LOGIN_INFO);
    }

    String token = tokenProvider.generateToken(company.getEmail(),
        String.valueOf(company.getMemberType()));
    return token;
  }

}
