package com.moving.shop.customer.controller;

import com.moving.shop.customer.application.CustomerSignInApplication;
import com.moving.shop.customer.domain.dto.SignInForm;
import com.moving.shop.customer.domain.dto.SignUpForm;
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
  public ResponseEntity<String> customerSignUp(@RequestBody SignInForm form) {
    return ResponseEntity.ok(customerSignInApplication.generateToken(form));
  }

}
