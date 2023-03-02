package com.moving.shop.servicechat.service.impl;

import com.moving.shop.common.exception.customexception.CompanyException;
import com.moving.shop.common.exception.customexception.CustomerException;
import com.moving.shop.common.exception.type.CompanyErrorCode;
import com.moving.shop.common.exception.type.CustomerErrorCode;
import com.moving.shop.common.security.TokenProvider;
import com.moving.shop.company.domain.entity.Company;
import com.moving.shop.company.domain.repository.CompanyRepository;
import com.moving.shop.customer.domain.entity.Customer;
import com.moving.shop.customer.domain.repository.CustomerRepository;
import com.moving.shop.servicechat.domain.entity.ChatRoom;
import com.moving.shop.servicechat.domain.repository.ChatRoomRepository;
import com.moving.shop.servicechat.service.ServiceChatRoomService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceChatRoomServiceImpl implements ServiceChatRoomService {

  private final ChatRoomRepository chatRoomRepository;
  private final CustomerRepository customerRepository;
  private final CompanyRepository companyRepository;
  private final TokenProvider tokenProvider;

  @Override
  public List<ChatRoom> findCustomersChatRoomList(String refinedToken) {

    String email = tokenProvider.getUsername(refinedToken);
    Customer customer = customerRepository.findByEmail(email)
        .orElseThrow(() -> new CustomerException(CustomerErrorCode.NOT_EXIST_MEMBER));

    return chatRoomRepository.findAllByCustomerId(customer.getId());
  }

  @Override
  public List<ChatRoom> findCompaniesChatRoomList(String refinedToken) {

    String email = tokenProvider.getUsername(refinedToken);
    Company company = companyRepository.findByEmail(email)
        .orElseThrow(() -> new CompanyException(CompanyErrorCode.NOT_EXIST_COMPANY_MEMBER));

    return chatRoomRepository.findAllByCompanyId(company.getId());
  }
}
