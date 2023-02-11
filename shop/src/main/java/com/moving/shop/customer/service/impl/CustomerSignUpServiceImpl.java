package com.moving.shop.customer.service.impl;

import com.moving.shop.customer.domain.dto.SignUpForm;
import com.moving.shop.customer.domain.entity.Customer;
import com.moving.shop.customer.domain.repository.CustomerRepository;
import com.moving.shop.customer.service.CustomerSignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerSignUpServiceImpl implements CustomerSignUpService {

  private final CustomerRepository customerRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Customer signUp(SignUpForm form) {

    String encodedPassword = this.passwordEncoder.encode(form.getPassword());
    return customerRepository.save(Customer.of(form, encodedPassword));
  }

  @Override
  public boolean isMailExist(String email) {
    return customerRepository.existsByEmail(email);
  }
}
