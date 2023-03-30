package com.moving.shop.product.service;

import static org.junit.jupiter.api.Assertions.*;

import com.moving.shop.common.exception.customexception.CompanyException;
import com.moving.shop.common.exception.type.CompanyErrorCode;
import com.moving.shop.common.security.TokenProvider;
import com.moving.shop.company.application.CompanySignInApplication;
import com.moving.shop.company.application.CompanySignUpApplication;
import com.moving.shop.company.domain.dto.CompanySignInForm;
import com.moving.shop.company.domain.dto.CompanySignUpForm;
import com.moving.shop.company.service.CompanySignUpService;
import com.moving.shop.customer.application.CustomerSignInApplication;
import com.moving.shop.customer.application.CustomerSignUpApplication;
import com.moving.shop.customer.domain.dto.CustomerRequestForm;
import com.moving.shop.customer.domain.dto.SignInForm;
import com.moving.shop.customer.domain.dto.SignUpForm;
import com.moving.shop.customer.domain.type.MemberType;
import com.moving.shop.customer.domain.type.ServiceCategory;
import com.moving.shop.customer.service.CustomerRequestService;
import com.moving.shop.product.domain.dto.AddProductOptionForm;
import com.moving.shop.product.domain.dto.AddServiceProductForm;
import com.moving.shop.product.domain.dto.UpdateProductOptionForm;
import com.moving.shop.product.domain.dto.UpdateServiceProductForm;
import com.moving.shop.product.domain.entity.ProductOption;
import com.moving.shop.product.domain.entity.ServiceProduct;
import com.moving.shop.product.domain.repository.ProductOptionRepository;
import com.moving.shop.product.domain.repository.ServiceProductRepository;
import com.moving.shop.servicechat.domain.entity.ChatRoom;
import com.moving.shop.servicechat.domain.repository.ChatRoomRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.sql.Update;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@SpringBootTest
class CompanyProductServiceTest {

  @Autowired
  private ChatRoomRepository chatRoomRepository;

  @Autowired
  private ProductOptionRepository productOptionRepository;

  @Autowired
  private ServiceProductRepository serviceProductRepository;

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
  void DELETE_SERVICE_PRODUCT_TEST() {

    //given
    String companiesJwt = makeCompanysJwt();
    String customersJwt = makeCustomersJwt();

    // (request create)
    CustomerRequestForm customerRequestForm = CustomerRequestForm.builder()
        .serviceAddress("경기 가평")
        .desiredDate(LocalDate.now().plusMonths(3))
        .detailRequest("test 요구사항입니다.")
        .placeArea(23)
        .serviceCategory("cleaning")
        .placeShape("house")
        .build();
    customerRequestService.addCustomerRequest(customersJwt, customerRequestForm);

    // (target serviceProduct create)
    List<AddProductOptionForm> addProductOptionForms = makeProductOptionForms();
    AddServiceProductForm addServiceProductForm = AddServiceProductForm.builder()
        .executeDate(LocalDateTime.now().plusMonths(3))
        .name("삭제기능테스트상품")
        .productOptions(addProductOptionForms)
        .outlineDescription("삭제기능테스트용으로 등록하는 상품입니다.")
        .productPrice(150000)
        .serviceRequestId(1L)
        .build();
    ServiceProduct serviceProduct = companyProductService.addServiceProduct(companiesJwt, addServiceProductForm);
    ServiceProduct foundServiceProduct = serviceProductRepository.findWithProductOptionsById(serviceProduct.getId())
        .orElseThrow(() -> new CompanyException(CompanyErrorCode.SERVICE_PRODUCT_NOT_EXIST));
    ChatRoom chatRoom = chatRoomRepository.findByServiceProduct_Id(serviceProduct.getId())
        .orElseThrow(() -> new CompanyException(CompanyErrorCode.CHAT_ROOM_INFO_NOT_EXIST));

    //when
    companyProductService.deleteServiceProduct(companiesJwt, foundServiceProduct.getId());
    boolean isServiceProductExists = serviceProductRepository.existsById(foundServiceProduct.getId());
    boolean isProductOption1Exists = productOptionRepository.existsById(foundServiceProduct.getProductOptions().get(0).getId());
    boolean isProductOption2Exists = productOptionRepository.existsById(foundServiceProduct.getProductOptions().get(1).getId());
    boolean isChatRoomExists = chatRoomRepository.existsById(chatRoom.getId());

    //then
    assertEquals(false, isServiceProductExists);
    assertEquals(false, isProductOption1Exists);
    assertEquals(false, isProductOption2Exists);
    assertEquals(false, isChatRoomExists);
  }

  @Test
  void UPDATE_SERVICE_PRODUCT_TEST() {

    //given
    String companiesJwt = makeCompanysJwt();
    String customersJwt = makeCustomersJwt();

    // (request create)
    CustomerRequestForm customerRequestForm = CustomerRequestForm.builder()
        .serviceAddress("경기 가평")
        .desiredDate(LocalDate.now().plusMonths(3))
        .detailRequest("test 요구사항입니다.")
        .placeArea(23)
        .serviceCategory("cleaning")
        .placeShape("house")
        .build();
    customerRequestService.addCustomerRequest(customersJwt, customerRequestForm);

    // (target serviceProduct create)
    List<AddProductOptionForm> addProductOptionForms = makeProductOptionForms();
    AddServiceProductForm addServiceProductForm = AddServiceProductForm.builder()
        .executeDate(LocalDateTime.now().plusMonths(3))
        .name("테스트상품")
        .productOptions(addProductOptionForms)
        .outlineDescription("테스트용으로 등록하는 상품입니다.")
        .productPrice(150000)
        .serviceRequestId(1L)
        .build();
    ServiceProduct serviceProduct = companyProductService.addServiceProduct(companiesJwt, addServiceProductForm);

    //when
    List<UpdateProductOptionForm> updateProductOptionForms = makeUpdateProductOptionForms(serviceProduct);
    UpdateServiceProductForm updateServiceProductForm = UpdateServiceProductForm.builder()
        .serviceProductId(serviceProduct.getId())
        .productOptions(updateProductOptionForms)
        .name("수정test용상품이름")
        .outLineDescription("수정test용description")
        .productPrice(500)
        .executeDate(LocalDateTime.now().plusMonths(7))
        .serviceRequestId(serviceProduct.getCustomerRequest().getId())
        .build();
    ServiceProduct updatedServiceProduct = companyProductService.updateServiceProduct(companiesJwt, updateServiceProductForm);
    ServiceProduct foundServiceProduct = serviceProductRepository.findWithProductOptionsById(
        updatedServiceProduct.getId())
        .orElseThrow(() -> new CompanyException(CompanyErrorCode.SERVICE_PRODUCT_NOT_EXIST));

    //then
    assertEquals(500, updatedServiceProduct.getProductPrice());
    assertEquals("수정test용상품이름", foundServiceProduct.getName());
    assertEquals("수정TEST를위한옵션명입니다", foundServiceProduct.getProductOptions().get(0).getName());
  }

  @Test
  void ADD_SERVICE_PRODUCT_TEST() {

    //given
    String companiesJwt = makeCompanysJwt();
    String customersJwt = makeCustomersJwt();

    // (request create)
    CustomerRequestForm customerRequestForm = CustomerRequestForm.builder()
        .serviceAddress("경기 가평")
        .desiredDate(LocalDate.now().plusMonths(3))
        .detailRequest("test 요구사항입니다.")
        .placeArea(23)
        .serviceCategory("cleaning")
        .placeShape("house")
        .build();
    customerRequestService.addCustomerRequest(customersJwt, customerRequestForm);

    //when
    List<AddProductOptionForm> addProductOptionForms = makeProductOptionForms();

    AddServiceProductForm addServiceProductForm = AddServiceProductForm.builder()
        .executeDate(LocalDateTime.now().plusMonths(3))
        .name("테스트상품")
        .productOptions(addProductOptionForms)
        .outlineDescription("테스트용으로 등록하는 상품입니다.")
        .productPrice(150000)
        .serviceRequestId(1L)
        .build();
    ServiceProduct serviceProduct = companyProductService.addServiceProduct(companiesJwt, addServiceProductForm);

    //then
    assertNotNull(serviceProduct);
    assertEquals(serviceProduct.getProductPrice(), addServiceProductForm.getProductPrice());
    assertNotNull(serviceProduct.getProductOptions());
    assertEquals(serviceProduct.getProductOptions().size(), 2);
  }

  @Test
  void COMPANY_SIGN_IN_SUCCESS() throws Exception {

    //given
    String companysJwt = makeCompanysJwt();

    //when
    //then
    String email = tokenProvider.getUsername(companysJwt);
    String memberType = tokenProvider.getKeyRoles(companysJwt);

    assertEquals(email, "company@google.com");
    assertEquals(memberType, String.valueOf(MemberType.COMPANY));
  }

  @Test
  void CUSTOMER_REQUEST_SUCCESS() throws Exception {

    //given
    String customersJwt = makeCustomersJwt();

    CustomerRequestForm customerRequestForm = CustomerRequestForm.builder()
        .serviceAddress("경기 가평")
        .desiredDate(LocalDate.now().plusMonths(3))
        .detailRequest("test 요구사항입니다.")
        .placeArea(23)
        .serviceCategory("cleaning")
        .placeShape("house")
        .build();

    //when
    CustomerRequestForm result = customerRequestService.addCustomerRequest(customersJwt, customerRequestForm);

    //then
    assertNotNull(result);
  }

  private List<UpdateProductOptionForm> makeUpdateProductOptionForms(ServiceProduct serviceProduct) {

    List<UpdateProductOptionForm> updateProductOptionForms = new ArrayList<>();

    for (ProductOption option : serviceProduct.getProductOptions()) {

      UpdateProductOptionForm updateProductOptionForm = UpdateProductOptionForm.builder()
          .serviceProductId(serviceProduct.getId())
          .productOptionId(option.getId())
          .name("수정TEST를위한옵션명입니다")
          .optionPrice(50)
          .build();
      updateProductOptionForms.add(updateProductOptionForm);
    }
    return updateProductOptionForms;
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

}