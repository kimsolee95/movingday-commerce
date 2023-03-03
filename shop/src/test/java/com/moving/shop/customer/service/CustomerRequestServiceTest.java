package com.moving.shop.customer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.moving.shop.common.exception.customexception.CustomerException;
import com.moving.shop.common.exception.type.CustomerErrorCode;
import com.moving.shop.common.security.TokenProvider;
import com.moving.shop.customer.application.CustomerSignInApplication;
import com.moving.shop.customer.application.CustomerSignUpApplication;
import com.moving.shop.customer.domain.dto.CustomerRequestForm;
import com.moving.shop.customer.domain.dto.CustomerRequestResponse;
import com.moving.shop.customer.domain.dto.SignInForm;
import com.moving.shop.customer.domain.dto.SignUpForm;
import com.moving.shop.customer.domain.entity.Customer;
import com.moving.shop.customer.domain.entity.CustomerRequest;
import com.moving.shop.customer.domain.repository.CustomerRepository;
import com.moving.shop.customer.domain.repository.CustomerRequestRepository;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@SpringBootTest
class CustomerRequestServiceTest {

  @Autowired
  private CustomerSignUpApplication customerSignUpApplication;

  @Autowired
  private CustomerSignInApplication customerSignInApplication;

  @Autowired
  private CustomerRequestService customerRequestService;

  @Autowired
  private CustomerRequestRepository customerRequestRepository;

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private TokenProvider tokenProvider;

  @Test
  void GET_CUSTOMER_REQUESTS_SUCCESS_TEST() {

    //given
    signUpCustomer();

    //when
    makeCustomerRequest();

    //then
    List<CustomerRequestResponse> customerRequestResponse = customerRequestService.getCustomerRequests();
    assertEquals(23, customerRequestResponse.get(0).getPlaceArea());
    assertEquals("경기 가평", customerRequestResponse.get(0).getServiceAddress());
    assertEquals("CLEANING", customerRequestResponse.get(0).getServiceCategory());
    assertEquals("HOUSE", customerRequestResponse.get(0).getPlaceShape());
  }

  private CustomerRequest makeCustomerRequest() {

    //given
    String customersJwt = loginCustomer();

    CustomerRequestForm form = CustomerRequestForm.builder()
        .serviceAddress("경기 가평")
        .desiredDate(LocalDate.now().plusMonths(3))
        .detailRequest("test 요구사항입니다.")
        .placeArea(23)
        .serviceCategory("cleaning")
        .placeShape("house")
        .build();

    //when
    Customer customer = customerRepository.findByEmail(tokenProvider.getUsername(customersJwt))
        .orElseThrow(() -> new CustomerException(CustomerErrorCode.NOT_EXIST_MEMBER));

    CustomerRequest customerRequest = CustomerRequest.builder()
        .customer(customer)
        .serviceAddress(form.getServiceAddress())
        .desiredDate(form.getDesiredDate())
        .detailRequest(form.getDetailRequest())
        .placeArea(form.getPlaceArea())
        .serviceCategory(form.getServiceCategoryType())
        .placeShape(form.getPlaceShapeType())
        .build();
    return customerRequestRepository.save(customerRequest);
  }

  private void signUpCustomer() {

    //given
    SignUpForm form = SignUpForm.builder()
        .email("testtest010101@naver.com")
        .name("testuser")
        .password("1122")
        .phone("01012345667").build();
    customerSignUpApplication.customerSignUp(form);
  }

  private String loginCustomer() {

    SignInForm signInForm = SignInForm.builder()
        .email("testtest010101@naver.com")
        .password("1122")
        .build();
    return customerSignInApplication.generateToken(signInForm);
  }

}