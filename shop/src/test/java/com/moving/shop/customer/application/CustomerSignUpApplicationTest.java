package com.moving.shop.customer.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.moving.shop.common.exception.customexception.CustomerException;
import com.moving.shop.customer.domain.dto.SignUpForm;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@SpringBootTest
class CustomerSignUpApplicationTest {

  @Autowired
  CustomerSignUpApplication customerSignUpApplication;

  @Test
  public void CUSTOMER_SIGN_UP_SUCCESS() throws Exception {

    //given
    SignUpForm form = SignUpForm.builder()
        .email("test@naver.com")
        .name("testuser")
        .password("1122")
        .phone("01012345667").build();

    //when
    String result = customerSignUpApplication.customerSignUp(form);

    //then
    assertEquals(result, "회원가입에 성공했습니다.");
  }

  @Test
  public void CUSTOMER_SIGNUP_FAIL_DUPLICATE_ID() throws Exception {

    //given
    SignUpForm form = SignUpForm.builder()
        .email("test@naver.com")
        .name("testuser")
        .password("1122")
        .phone("01012345667").build();
    String result = customerSignUpApplication.customerSignUp(form);

    //duplicate form
    SignUpForm duplicateForm = SignUpForm.builder()
        .email("test@naver.com")
        .name("testuser")
        .password("1122")
        .phone("01012345667").build();

    //when

    //then
    Exception exception = assertThrows(CustomerException.class,
        () -> customerSignUpApplication.customerSignUp(duplicateForm));
  }

}