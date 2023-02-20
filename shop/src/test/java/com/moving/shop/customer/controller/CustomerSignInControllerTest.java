package com.moving.shop.customer.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.moving.shop.common.exception.customexception.CustomerException;
import com.moving.shop.common.security.TokenProvider;
import com.moving.shop.customer.application.CustomerSignInApplication;
import com.moving.shop.customer.application.CustomerSignUpApplication;
import com.moving.shop.customer.domain.dto.SignInForm;
import com.moving.shop.customer.domain.dto.SignUpForm;
import com.moving.shop.customer.domain.type.MemberType;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@SpringBootTest
class CustomerSignInControllerTest {

  @Autowired
  CustomerSignInApplication customerSignInApplication;

  @Autowired
  CustomerSignUpApplication customerSignUpApplication;

  @Autowired
  TokenProvider tokenProvider;

  public final String TOKEN_PREFIX = "Bearer ";

  @Test
  public void CUSTOMER_SIGN_IN_SUCCESS() throws Exception {

    //1) given
    //1.회원가입
    //1-1) given
    SignUpForm form = SignUpForm.builder()
        .email("testtest0101@naver.com")
        .name("testuser")
        .password("1122")
        .phone("01012345667").build();
    //1-2) when
    String result = customerSignUpApplication.customerSignUp(form);
    //1-3) then
    assertEquals(result, "회원가입에 성공했습니다.");

    //given -> 회원가입 후, 해당 정보 setting
    SignInForm signInForm = SignInForm.builder()
        .email("testtest0101@naver.com")
        .password("1122")
        .build();

    //2) when
    String jwt = customerSignInApplication.generateToken(signInForm);

    //then
    String getUsername = tokenProvider.getUsername(jwt);
    String getMemberRole = tokenProvider.getKeyRoles(jwt);

    assertEquals("testtest0101@naver.com", getUsername);
    assertEquals(String.valueOf(MemberType.CUSTOMER), getMemberRole);
  }

  @Test
  public void CUSTOMER_SIGN_IN_FAIL() throws Exception {

    //1) given
    //1.회원가입
    //1-1) given
    SignUpForm form = SignUpForm.builder()
        .email("testtest0101@naver.com")
        .name("testuser")
        .password("1122")
        .phone("01012345667").build();
    //1-2) when
    String result = customerSignUpApplication.customerSignUp(form);
    //1-3) then
    assertEquals(result, "회원가입에 성공했습니다.");

    //given -> 회원가입 후, 해당 정보 setting
    SignInForm signInForm = SignInForm.builder()
        .email("testtest0101@naver.com")
        .password("0000000000000")
        .build();

    //2) when

    //3) then
    Exception exception = assertThrows(CustomerException.class,
        () -> customerSignInApplication.generateToken(signInForm));
  }

}