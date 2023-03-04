package com.moving.shop.product.service.impl;

import com.moving.shop.common.exception.customexception.CustomerException;
import com.moving.shop.common.exception.customexception.OrderException;
import com.moving.shop.common.exception.type.CustomerErrorCode;
import com.moving.shop.common.security.TokenProvider;
import com.moving.shop.customer.domain.entity.Customer;
import com.moving.shop.customer.domain.repository.CustomerRepository;
import com.moving.shop.product.domain.entity.ServiceProduct;
import com.moving.shop.product.domain.repository.ServiceProductRepository;
import com.moving.shop.product.service.CustomerProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerProductServiceImpl implements CustomerProductService {

  private final TokenProvider tokenProvider;
  private final CustomerRepository customerRepository;
  private final ServiceProductRepository serviceProductRepository;

  @Override
  public List<ServiceProduct> findByCustomerId(String refinedToken) {

    String email = tokenProvider.getUsername(refinedToken);
    Customer customer = customerRepository.findByEmail(email)
        .orElseThrow(() -> new CustomerException(CustomerErrorCode.NOT_EXIST_MEMBER));

    return serviceProductRepository.findByCustomerId(customer.getId());
  }

  @Override
  public ServiceProduct findWithProductOptionsById(Long serviceProductId) {

    ServiceProduct serviceProductWithOptions = serviceProductRepository.findWithProductOptionsById(serviceProductId)
        .orElseThrow(() -> new CustomerException(CustomerErrorCode.NOT_CORRECT_INPUT));

    return serviceProductWithOptions;
  }
}
