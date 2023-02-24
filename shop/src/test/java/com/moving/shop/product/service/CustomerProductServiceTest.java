package com.moving.shop.product.service;

import static org.junit.jupiter.api.Assertions.*;

import com.moving.shop.common.exception.customexception.CustomerException;
import com.moving.shop.common.exception.type.CustomerErrorCode;
import com.moving.shop.common.security.TokenProvider;
import com.moving.shop.company.application.CompanySignInApplication;
import com.moving.shop.company.domain.dto.CompanySignInForm;
import com.moving.shop.company.domain.dto.CompanySignUpForm;
import com.moving.shop.company.service.CompanySignUpService;
import com.moving.shop.customer.application.CustomerSignInApplication;
import com.moving.shop.customer.application.CustomerSignUpApplication;
import com.moving.shop.customer.domain.dto.CustomerRequestForm;
import com.moving.shop.customer.domain.dto.SignInForm;
import com.moving.shop.customer.domain.dto.SignUpForm;
import com.moving.shop.customer.domain.entity.Customer;
import com.moving.shop.customer.domain.entity.CustomerRequest;
import com.moving.shop.customer.domain.repository.CustomerRepository;
import com.moving.shop.customer.domain.repository.CustomerRequestRepository;
import com.moving.shop.customer.service.CustomerRequestService;
import com.moving.shop.product.domain.dto.AddProductOptionForm;
import com.moving.shop.product.domain.dto.AddServiceProductForm;
import com.moving.shop.product.domain.entity.ServiceProduct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
class CustomerProductServiceTest {

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private CustomerRequestRepository customerRequestRepository;

  @Autowired
  private CustomerProductService customerProductService;

  @Autowired
  private CompanyProductService companyProductService;

  @Autowired
  private CompanySignInApplication companySignInApplication;

  @Autowired
  private CompanySignUpService companySignUpService;

  @Autowired
  private CustomerSignUpApplication customerSignUpApplication;

  @Autowired
  private CustomerSignInApplication customerSignInApplication;

  @Autowired
  private CustomerRequestService customerRequestService;

  @Autowired
  private TokenProvider tokenProvider;

  @Test
  void GET_PRODUCT_FOR_CUSTOMERS_REQUEST_TEST() {

    //given
    ServiceProduct firstServiceProduct = addServiceProduct();

    //when
    String customersJwt = loginCustomer();
    List<ServiceProduct> serviceProductsForRequest = customerProductService.findByCustomerId(customersJwt);

    //then
    assertEquals(firstServiceProduct.getProductPrice(), 150000);
    assertNotNull(serviceProductsForRequest);
    assertEquals(serviceProductsForRequest.size(), 1);
    assertEquals(serviceProductsForRequest.get(0).getProductPrice(), 150000);
    assertEquals(serviceProductsForRequest.get(0).getProductOptions().size(), 2);
    assertEquals(serviceProductsForRequest.get(0).getProductOptions().get(0).getName(), "상품옵션1");
  }

  private ServiceProduct addServiceProduct() {

    String companysJwt = makeCompanysJwt();
    CustomerRequest customerRequest = makeCustomerRequest();

    List<AddProductOptionForm> addProductOptionForms = makeProductOptionForms();
    AddServiceProductForm addServiceProductForm = AddServiceProductForm.builder()
        .executeDate(LocalDateTime.now().plusMonths(3))
        .name("테스트상품")
        .productOptions(addProductOptionForms)
        .outlineDescription("테스트용으로 등록하는 상품입니다.")
        .productPrice(150000)
        .serviceRequestId(customerRequest.getId())
        .build();
    return companyProductService.addServiceProduct(companysJwt, addServiceProductForm);
  }


  private CustomerRequest makeCustomerRequest() {

    //given
    String customersJwt = makeCustomersJwt();

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

  private List<AddProductOptionForm> makeProductOptionForms() {

    List<AddProductOptionForm> addProductOptionForms = new ArrayList<>();
    AddProductOptionForm addProductOption1 = AddProductOptionForm.builder().name("상품옵션1")
        .optionPrice(500).build();
    AddProductOptionForm addProductOption2 = AddProductOptionForm.builder().name("상품옵션1")
        .optionPrice(500).build();
    addProductOptionForms.add(addProductOption1);
    addProductOptionForms.add(addProductOption2);
    return addProductOptionForms;
  }

  private String makeCompanysJwt() {

    //given
    CompanySignUpForm form = CompanySignUpForm.builder()
        .serviceCategory("cleaning")
        .email("company@google.com")
        .password("1111")
        .address("서울시 영등포구")
        .addressDetail("가평읍")
        .zipcode("119662")
        .tel("01022223333")
        .introduction("test용 업체 회원가입")
        .businessNumber("1123345565")
        .build();
    companySignUpService.signUp(form);

    CompanySignInForm loginForm = CompanySignInForm.builder()
        .email("company@google.com")
        .password("1111")
        .build();
    String jwt = companySignInApplication.generateToken(loginForm);
    return jwt;
  }

  private String makeCustomersJwt() {

    //given
    SignUpForm form = SignUpForm.builder()
        .email("testtest0101@naver.com")
        .name("testuser")
        .password("1122")
        .phone("01012345667").build();
    customerSignUpApplication.customerSignUp(form);

    SignInForm signInForm = SignInForm.builder()
        .email("testtest0101@naver.com")
        .password("1122")
        .build();
    String jwt = customerSignInApplication.generateToken(signInForm);

    return jwt;
  }

  private String loginCustomer() {

    SignInForm signInForm = SignInForm.builder()
        .email("testtest0101@naver.com")
        .password("1122")
        .build();
    return customerSignInApplication.generateToken(signInForm);
  }

}