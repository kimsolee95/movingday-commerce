package com.moving.shop.company.controller;

import com.moving.shop.company.application.CompanySignUpApplication;
import com.moving.shop.company.domain.dto.CompanySignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/signup/company")
public class CompanySignUpController {

  private final CompanySignUpApplication companySignUpApplication;

  @PostMapping
  public ResponseEntity<?> companySignUp(@RequestPart("companySignUpForm") CompanySignUpForm form,
      @RequestPart MultipartFile uploadFile) {
    return ResponseEntity.ok(companySignUpApplication.companySignUp(form, uploadFile));
  }

}
