package com.moving.shop.customer.service.impl;

import com.moving.shop.common.exception.customexception.CustomerException;
import com.moving.shop.common.exception.type.CustomerErrorCode;
import com.moving.shop.customer.domain.dto.SignInForm;
import com.moving.shop.customer.domain.dto.SignUpForm;
import com.moving.shop.customer.domain.entity.Customer;
import com.moving.shop.customer.domain.repository.CustomerRepository;
import com.moving.shop.customer.service.CustomerSignUpService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerSignUpServiceImpl implements CustomerSignUpService {

  private final CustomerRepository customerRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Customer signUp(SignUpForm form) {

    String encodedPassword = passwordEncoder.encode(form.getPassword());
    return customerRepository.save(Customer.of(form, encodedPassword));
  }

  @Override
  public boolean isMailExist(String email) {
    return customerRepository.existsByEmail(email);
  }

  @Override
  public Customer findByEmail(SignInForm form) {

    return customerRepository.findByEmail(form.getEmail())
        .orElseThrow(() -> new CustomerException(CustomerErrorCode.EMAIL_ALREADY_EXIST));
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    Customer customer = customerRepository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("입력한 이메일을 사용하는 회원이 없습니다."));

    //고객 권한 부여
    List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
    grantedAuthorityList.add(new SimpleGrantedAuthority("CUSTOMER"));

    //(String username, String password, Collection<? extends GrantedAuthority> authorities)
    return new User(customer.getEmail(), customer.getPassword(), grantedAuthorityList);
  }
}
