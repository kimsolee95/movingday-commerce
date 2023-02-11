package com.moving.shop.customer.application;

import com.moving.shop.common.exception.customexception.CustomerException;
import com.moving.shop.common.exception.type.CustomerErrorCode;
import com.moving.shop.common.security.TokenProvider;
import com.moving.shop.customer.domain.dto.SignInForm;
import com.moving.shop.customer.domain.entity.Customer;
import com.moving.shop.customer.service.CustomerSignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerSignInApplication {

  private final TokenProvider tokenProvider;
  private final CustomerSignUpService customerSignUpService;
  private final PasswordEncoder passwordEncoder;

  public String generateToken(SignInForm form) {

    Customer customer = customerSignUpService.findByEmail(form);

    if (!this.passwordEncoder.matches(form.getPassword(), customer.getPassword())) { //CharSequence rawPassword, String encodedPassword
      throw new CustomerException(CustomerErrorCode.NOT_MATCH_LOGIN_INFO);
    }

    String token = this.tokenProvider.generateToken(customer.getEmail(),
        String.valueOf(customer.getMemberType()));
    return token;
  }

}
