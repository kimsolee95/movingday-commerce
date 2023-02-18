package com.moving.shop.company.controller;

import com.moving.shop.company.application.CompanySignInApplication;
import com.moving.shop.company.domain.dto.CompanySignInForm;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/signin/company")
@RequiredArgsConstructor
public class CompanySignInController {

  private final CompanySignInApplication companySignInApplication;

  @PostMapping
  public ResponseEntity<String> companySignIn(@Valid @RequestBody CompanySignInForm form) {
    return ResponseEntity.ok(companySignInApplication.generateToken(form));
  }

}
