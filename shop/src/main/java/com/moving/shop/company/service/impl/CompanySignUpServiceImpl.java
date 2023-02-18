package com.moving.shop.company.service.impl;

import com.moving.shop.common.exception.customexception.CompanyException;
import com.moving.shop.common.exception.type.CompanyErrorCode;
import com.moving.shop.company.domain.dto.CompanySignUpForm;
import com.moving.shop.company.domain.entity.Company;
import com.moving.shop.company.domain.repository.CompanyRepository;
import com.moving.shop.company.service.CompanySignUpService;
import com.moving.shop.customer.domain.type.MemberType;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanySignUpServiceImpl implements CompanySignUpService {

  private final CompanyRepository companyRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Company signUp(CompanySignUpForm form) {

    String encodedPassword = passwordEncoder.encode(form.getPassword());
    return companyRepository.save(Company.of(form, encodedPassword));
  }

  @Override
  public boolean existsByEmail(String email) {
    return companyRepository.existsByEmail(email);
  }

  @Override
  public Company findByEmail(String email) {
    return companyRepository.findByEmail(email)
        .orElseThrow(() -> new CompanyException(CompanyErrorCode.NOT_MATCH_LOGIN_INFO));
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    Company company = companyRepository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("입력한 이메일을 사용하는 업체회원이 없습니다."));

    //업체 회원 권한 부여
    List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
    grantedAuthorityList.add(new SimpleGrantedAuthority(String.valueOf(MemberType.COMPANY)));

    //public User(String username, String password, Collection<? extends GrantedAuthority> authorities)
    return new User(company.getEmail(), company.getPassword(), grantedAuthorityList);
  }
}
