package com.moving.shop.company.controller;

import com.moving.shop.company.application.CompanySignInApplication;
import com.moving.shop.company.domain.dto.CompanySignInForm;
import io.swagger.annotations.ApiOperation;
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
  @ApiOperation(value="업체 로그인 API", notes = "업체 권한을 부여한 JWT를 반환합니다.")
  public ResponseEntity<String> companySignIn(@Valid @RequestBody CompanySignInForm form) {
    return ResponseEntity.ok(companySignInApplication.generateToken(form));
  }

}
