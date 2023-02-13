package com.moving.shop.customer.service;

import com.moving.shop.customer.domain.dto.SignInForm;
import com.moving.shop.customer.domain.dto.SignUpForm;
import com.moving.shop.customer.domain.entity.Customer;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomerSignUpService extends UserDetailsService {

  Customer signUp(SignUpForm form);

  boolean isMailExist(String email);

  Customer findByEmail(SignInForm form);
}
