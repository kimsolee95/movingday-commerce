package com.moving.shop.customer.service.impl;

import com.moving.shop.customer.domain.dto.SignUpForm;
import com.moving.shop.customer.domain.entity.Customer;
import com.moving.shop.customer.domain.repository.CustomerRepository;
import com.moving.shop.customer.service.CustomerSignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerSignUpServiceImpl implements CustomerSignUpService {

  private final CustomerRepository customerRepository;

  @Override
  public Customer signUp(SignUpForm form) {
    return customerRepository.save(Customer.from(form));
  }

  @Override
  public boolean isMailExist(String email) {
    return customerRepository.existsByEmail(email);
  }
}
