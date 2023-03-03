package com.moving.shop.customer.service.impl;

import com.moving.shop.common.exception.customexception.CustomerException;
import com.moving.shop.common.exception.type.CustomerErrorCode;
import com.moving.shop.common.security.TokenProvider;
import com.moving.shop.customer.domain.dto.CustomerInfo;
import com.moving.shop.customer.domain.dto.CustomerRequestForm;
import com.moving.shop.customer.domain.dto.CustomerRequestResponse;
import com.moving.shop.customer.domain.entity.Customer;
import com.moving.shop.customer.domain.entity.CustomerRequest;
import com.moving.shop.customer.domain.repository.CustomerRepository;
import com.moving.shop.customer.domain.repository.CustomerRequestRepository;
import com.moving.shop.customer.service.CustomerRequestService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerRequestServiceImpl implements CustomerRequestService {

  private final CustomerRequestRepository customerRequestRepository;
  private final TokenProvider tokenProvider;
  private final CustomerRepository customerRepository;

  @Override
  @Transactional
  public CustomerRequestForm addCustomerRequest(String refinedToken, CustomerRequestForm form) {

    Customer customer = customerRepository.findByEmail(tokenProvider.getUsername(refinedToken))
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
    customerRequestRepository.save(customerRequest);
    return form;
  }

  @Override
  public CustomerInfo getCustomerInfo(String refinedToken) {

    Customer customer = customerRepository.findByEmail(tokenProvider.getUsername(refinedToken))
        .orElseThrow(() -> new CustomerException(CustomerErrorCode.NOT_EXIST_MEMBER));

    return CustomerInfo.builder()
        .email(customer.getEmail())
        .name(customer.getName())
        .phone(customer.getPhone())
        .build();
  }

  @Override
  public List<CustomerRequestResponse> getCustomerRequests() {

    List<CustomerRequest> customerRequests = customerRequestRepository.findAll(Sort.by(
        Sort.Direction.DESC, "id"));
    if (customerRequests.isEmpty()) {
      return null;
    }

    return customerRequests.stream()
        .map(customerRequest -> CustomerRequestResponse.from(customerRequest)).collect(Collectors.toList());
  }
}
