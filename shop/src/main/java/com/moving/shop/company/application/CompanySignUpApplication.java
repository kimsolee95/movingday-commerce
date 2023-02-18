package com.moving.shop.company.application;

import com.moving.shop.common.exception.customexception.CompanyException;
import com.moving.shop.common.exception.type.CompanyErrorCode;
import com.moving.shop.company.domain.dto.CompanySignUpForm;
import com.moving.shop.company.domain.entity.Company;
import com.moving.shop.company.service.CompanyImgService;
import com.moving.shop.company.service.CompanySignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CompanySignUpApplication {

  private final CompanySignUpService companySignUpService;
  private final CompanyImgService companyImgService;

  @Transactional
  public String
  companySignUp(CompanySignUpForm companySignUpForm, MultipartFile uploadFile) {

    if (companySignUpService.existsByEmail(companySignUpForm.getEmail())) {
      throw new CompanyException(CompanyErrorCode.EMAIL_ALREADY_EXIST);
    }

    Company saveCompany = companySignUpService.signUp(companySignUpForm);

    try {
      companyImgService.saveCompanyImg(saveCompany, uploadFile);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return "업체 회원 가입을 완료하였습니다.";
  }

}
