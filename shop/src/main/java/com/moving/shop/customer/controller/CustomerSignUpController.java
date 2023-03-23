package com.moving.shop.customer.controller;

import com.moving.shop.customer.application.CustomerSignUpApplication;
import com.moving.shop.customer.domain.dto.SignUpForm;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/signup")
@RequiredArgsConstructor
public class CustomerSignUpController {

  private final CustomerSignUpApplication customerSignUpApplication;

  @PostMapping("/customer")
  @ApiOperation(value="고객 회원가입 API", notes = "고객 회원 유형으로 회원가입을 합니다.")
  public ResponseEntity<String> customerSignUp(@Valid @RequestBody SignUpForm form) {
    return ResponseEntity.ok(customerSignUpApplication.customerSignUp(form));
  }

}
