package com.moving.shop.product.service.impl;

import com.moving.shop.common.exception.customexception.CompanyException;
import com.moving.shop.common.exception.type.CompanyErrorCode;
import com.moving.shop.common.security.TokenProvider;
import com.moving.shop.company.domain.entity.Company;
import com.moving.shop.company.domain.repository.CompanyRepository;
import com.moving.shop.customer.domain.entity.CustomerRequest;
import com.moving.shop.customer.domain.repository.CustomerRequestRepository;
import com.moving.shop.product.domain.dto.AddServiceProductForm;
import com.moving.shop.product.domain.dto.CompaniesServiceProduct;
import com.moving.shop.product.domain.dto.UpdateProductOptionForm;
import com.moving.shop.product.domain.dto.UpdateServiceProductForm;
import com.moving.shop.product.domain.entity.ProductOption;
import com.moving.shop.product.domain.entity.ServiceProduct;
import com.moving.shop.product.domain.repository.ServiceProductRepository;
import com.moving.shop.product.service.CompanyProductService;
import com.moving.shop.servicechat.domain.entity.ChatRoom;
import com.moving.shop.servicechat.domain.redis.RedisChatRoomRepository;
import com.moving.shop.servicechat.domain.repository.ChatRoomRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyProductServiceImpl implements CompanyProductService {

  private final CompanyRepository companyRepository;
  private final ServiceProductRepository serviceProductRepository;
  private final CustomerRequestRepository customerRequestRepository;
  private final TokenProvider tokenProvider;
  private final ChatRoomRepository chatRoomRepository;
  private final RedisChatRoomRepository redisChatRoomRepository;

  @Transactional
  @Override
  public ServiceProduct addServiceProduct(String refinedToken, AddServiceProductForm form) {

    Company company = getCompany(refinedToken);
    //erd 수정으로 인한 코드 수정 (연관관계 수정)
    CustomerRequest customerRequest = customerRequestRepository.findById(form.getServiceRequestId())
        .orElseThrow(() -> new CompanyException(CompanyErrorCode.SERVICE_REQUEST_NOT_EXIST));

    ServiceProduct serviceProduct = serviceProductRepository.save(ServiceProduct.of(company, form, customerRequest));

    //업체가 상품 등록 시, 상품 문의에 대한 고객과의 1:1 채팅방 생성
    ChatRoom chatRoom = chatRoomRepository.save(
        ChatRoom.create(serviceProduct, form.getName(), customerRequest.getCustomer().getId(),
            company.getId()));
    redisChatRoomRepository.createChatRoom(chatRoom); //redis

    //상품 save
    return serviceProduct;
  }

  @Transactional
  @Override
  public ServiceProduct updateServiceProduct(String refinedToken, UpdateServiceProductForm form) {

    //data check
    String email = tokenProvider.getUsername(refinedToken);
    Company company = companyRepository.findByEmail(email)
        .orElseThrow(() -> new CompanyException(CompanyErrorCode.NOT_EXIST_COMPANY_MEMBER));

    CustomerRequest customerRequest = customerRequestRepository.findById(form.getServiceRequestId())
        .orElseThrow(() -> new CompanyException(CompanyErrorCode.SERVICE_REQUEST_NOT_EXIST));

    ServiceProduct serviceProduct = serviceProductRepository.findWithProductOptionsById(form.getServiceProductId())
        .orElseThrow(() -> new CompanyException(CompanyErrorCode.SERVICE_PRODUCT_NOT_EXIST));

    if (serviceProduct.isPurchaseYn()) {
      throw new CompanyException(CompanyErrorCode.ALREADY_ORDERED_PRODUCT);
    }

    //상품 옵션 정보 update
    for (UpdateProductOptionForm optionForm : form.getProductOptions()) {

      ProductOption productOption = serviceProduct.getProductOptions().stream()
          .filter(option -> option.getId().equals(optionForm.getProductOptionId()))
          .findFirst().orElseThrow(() -> new CompanyException(CompanyErrorCode.SERVICE_PRODUCT_NOT_EXIST));
      productOption.updateProductOption(optionForm);
    }

    //상품 정보 update
    return serviceProduct.updateServiceProduct(form);
  }

  @Transactional
  @Override
  public void deleteServiceProduct(String refinedToken, Long serviceProductId) {

    //data check
    String email = tokenProvider.getUsername(refinedToken);
    if (!companyRepository.existsByEmail(email)) {
      throw new CompanyException(CompanyErrorCode.NOT_EXIST_COMPANY_MEMBER);
    }

    ChatRoom chatRoom = chatRoomRepository.findByServiceProduct_Id(serviceProductId)
        .orElseThrow(() -> new CompanyException(CompanyErrorCode.CHAT_ROOM_INFO_NOT_EXIST));

    //chat room delete
    chatRoomRepository.deleteById(chatRoom.getId());

    //redis chat room delete
    redisChatRoomRepository.deleteChatRoom(chatRoom.getId());

    //product and option delete
    serviceProductRepository.deleteById(serviceProductId);
  }

  @Override
  public List<CompaniesServiceProduct> selectNotPurchasedProduct(String refinedToken) {

    Company company = getCompany(refinedToken);
    return serviceProductRepository.findAllByCompanyIdAndPurchaseYnFalse(company.getId());
  }

  @Override
  public List<CompaniesServiceProduct> selectPurchasedProduct(String refinedToken) {

    Company company = getCompany(refinedToken);
    return serviceProductRepository.findAllByCompanyIdAndPurchaseYnTrue(company.getId());
  }

  private Company getCompany(String refinedToken) {

    String email = tokenProvider.getUsername(refinedToken);
    Company company = companyRepository.findByEmail(email)
        .orElseThrow(() -> new CompanyException(CompanyErrorCode.NOT_EXIST_COMPANY_MEMBER));
    return company;
  }

}
