package com.moving.shop.customer.service;

import com.moving.shop.customer.domain.dto.SignUpForm;
import com.moving.shop.customer.domain.entity.Customer;

public interface CustomerSignUpService {

  Customer signUp(SignUpForm form);

  boolean isMailExist(String email);
}
