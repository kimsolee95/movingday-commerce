package com.moving.shop.order.service;

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
import com.moving.shop.order.domain.dto.AddOrderProductForm;
import com.moving.shop.order.domain.dto.AddOrderProductOptionForm;
import com.moving.shop.order.domain.dto.CompleteOrderForm;
import com.moving.shop.order.domain.entity.CompletionOrder;
import com.moving.shop.order.domain.entity.ServiceOrder;
import com.moving.shop.order.domain.repository.OrderProductOptionRepository;
import com.moving.shop.order.domain.repository.OrderProductRepository;
import com.moving.shop.order.domain.type.OrderStatus;
import com.moving.shop.product.domain.dto.AddProductOptionForm;
import com.moving.shop.product.domain.dto.AddServiceProductForm;
import com.moving.shop.product.domain.entity.ServiceProduct;
import com.moving.shop.product.service.CompanyProductService;
import com.moving.shop.product.service.CustomerProductService;
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
class CompanyOrderServiceTest {

  @Autowired
  private CompanyOrderService companyOrderService;

  @Autowired
  private OrderProductOptionRepository orderProductOptionRepository;

  @Autowired
  private OrderProductRepository orderProductRepository;

  @Autowired
  private CustomerOrderService customerOrderService;

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
  void COMPLETE_ORDER_SUCCESS() {

    //given
    ServiceProduct serviceProduct = addServiceProduct();

    //when
    String customersJwt = loginCustomer();
    AddOrderProductForm orderProductForm = makeOrderFormWithAllOptions(customersJwt);
    ServiceOrder serviceOrder = customerOrderService.createOrder(orderProductForm);

    // 업체측에서 주문 완료 확인 data 생성하기
    String companiesJwt = loginCompany();
    CompletionOrder completionOrder = companyOrderService.completeServiceOrder(companiesJwt,
        CompleteOrderForm.builder().serviceOrderId(serviceOrder.getId()).build());

    //then
    assertEquals(completionOrder.getServiceOrder().getId(), serviceOrder.getId());
    assertEquals(completionOrder.getServiceOrder().getOrderStatus(), OrderStatus.COMPLETE);
    assertEquals(completionOrder.isCompanyCheckYn(), true);
    assertEquals(completionOrder.isCustomerCheckYn(), false);
  }

  private AddOrderProductForm makeOrderFormWithAllOptions(String customersJwt) {

    //고객 앞으로 온 서비스 상품 정보를 토대로 주문 form 생성
    List<ServiceProduct> serviceProductsForRequest = customerProductService.findByCustomerId(customersJwt);
    //서비스 상품 정보 내의 모든 옵션 포함하여 주문 form 생성
    List<AddOrderProductOptionForm> options = new ArrayList<>();
    for (int i=0; i<serviceProductsForRequest.get(0).getProductOptions().size(); i++) {

      AddOrderProductOptionForm optionForm = AddOrderProductOptionForm.builder()
          .productOptionId(serviceProductsForRequest.get(0).getProductOptions().get(i).getId())
          .optionPrice(serviceProductsForRequest.get(0).getProductOptions().get(i).getOptionPrice())
          .build();
      options.add(optionForm);
    }

    AddOrderProductForm orderForm = AddOrderProductForm.builder()
        .customerRequestId(serviceProductsForRequest.get(0).getCustomerRequest().getId())
        .serviceProductId(serviceProductsForRequest.get(0).getId())
        .productPrice(serviceProductsForRequest.get(0).getProductPrice())
        .addOrderProductOptionForms(options)
        .build();
    return orderForm;
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
    AddProductOptionForm addProductOption2 = AddProductOptionForm.builder().name("상품옵션2")
        .optionPrice(1000).build();
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

  private String loginCompany() {

    CompanySignInForm signInForm = CompanySignInForm
        .builder()
        .email("company@google.com")
        .password("1111")
        .build();
    return companySignInApplication.generateToken(signInForm);
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