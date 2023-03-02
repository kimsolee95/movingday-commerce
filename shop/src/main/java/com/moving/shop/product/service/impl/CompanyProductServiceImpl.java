package com.moving.shop.product.service.impl;

import com.moving.shop.common.exception.customexception.CompanyException;
import com.moving.shop.common.exception.type.CompanyErrorCode;
import com.moving.shop.common.security.TokenProvider;
import com.moving.shop.company.domain.entity.Company;
import com.moving.shop.company.domain.repository.CompanyRepository;
import com.moving.shop.customer.domain.entity.CustomerRequest;
import com.moving.shop.customer.domain.repository.CustomerRequestRepository;
import com.moving.shop.product.domain.dto.AddServiceProductForm;
import com.moving.shop.product.domain.entity.ServiceProduct;
import com.moving.shop.product.domain.repository.ServiceProductRepository;
import com.moving.shop.product.service.CompanyProductService;
import com.moving.shop.servicechat.domain.entity.ChatRoom;
import com.moving.shop.servicechat.domain.redis.RedisChatRoomRepository;
import com.moving.shop.servicechat.domain.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyProductServiceImpl implements CompanyProductService {

  private final CompanyRepository companyRepository;
  private final ServiceProductRepository serviceProductRepository;
  private final CustomerRequestRepository customerRequestRepository;
  private final TokenProvider tokenProvider;
  private final ChatRoomRepository chatRoomRepository;
  private final RedisChatRoomRepository redisChatRoomRepository;

  @Override
  public ServiceProduct addServiceProduct(String refinedToken, AddServiceProductForm form) {

    String email = tokenProvider.getUsername(refinedToken);
    Company company = companyRepository.findByEmail(email)
        .orElseThrow(() -> new CompanyException(CompanyErrorCode.NOT_EXIST_COMPANY_MEMBER));

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
}
