package com.moving.shop.customer.service;

import com.moving.shop.customer.domain.dto.CustomerInfo;
import com.moving.shop.customer.domain.dto.CustomerRequestForm;
import com.moving.shop.customer.domain.dto.CustomerRequestResponse;
import com.moving.shop.customer.domain.entity.CustomerRequest;
import java.util.List;

public interface CustomerRequestService {

  CustomerRequestForm addCustomerRequest(String refinedToken, CustomerRequestForm form);

  CustomerInfo getCustomerInfo(String refinedToken);

  List<CustomerRequestResponse> getCustomerRequests();
}
