package com.moving.shop.customer.service;

import com.moving.shop.customer.domain.dto.CustomerRequestForm;
import com.moving.shop.customer.domain.entity.CustomerRequest;

public interface CustomerRequestService {

  CustomerRequestForm addCustomerRequest(String refinedToken, CustomerRequestForm form);
}
