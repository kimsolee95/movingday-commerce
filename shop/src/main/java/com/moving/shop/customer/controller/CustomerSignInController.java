package com.moving.shop.customer.controller;

import com.moving.shop.customer.application.CustomerSignInApplication;
import com.moving.shop.customer.domain.dto.SignInForm;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/signin")
@RequiredArgsConstructor
public class CustomerSignInController {

  private final CustomerSignInApplication customerSignInApplication;

  @PostMapping("/customer")
  @ApiOperation(value="고객회원 로그인 API", notes = "고객 권한을 부여한 JWT를 반환합니다.")
  public ResponseEntity<String> customerSignIn(@Valid @RequestBody SignInForm form) {
    return ResponseEntity.ok(customerSignInApplication.generateToken(form));
  }

}
