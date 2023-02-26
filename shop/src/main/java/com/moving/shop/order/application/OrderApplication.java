package com.moving.shop.order.application;

import com.moving.shop.common.exception.customexception.CustomerException;
import com.moving.shop.common.exception.customexception.OrderException;
import com.moving.shop.common.exception.type.CustomerErrorCode;
import com.moving.shop.common.exception.type.OrderErrorCode;
import com.moving.shop.common.security.TokenProvider;
import com.moving.shop.customer.domain.dto.ChangeCashForm;
import com.moving.shop.customer.domain.entity.Customer;
import com.moving.shop.customer.domain.repository.CustomerRepository;
import com.moving.shop.customer.service.CustomerCashService;
import com.moving.shop.order.domain.dto.AddOrderProductForm;
import com.moving.shop.order.domain.entity.ServiceOrder;
import com.moving.shop.order.service.CustomerOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderApplication {

  private final CustomerOrderService customerOrderService;
  private final CustomerCashService customerCashService;
  private final CustomerRepository customerRepository;
  private final TokenProvider tokenProvider;

  @Transactional
  public ServiceOrder takeOrder(String refinedToken, AddOrderProductForm form) {

    //1. member validation
    String email = tokenProvider.getUsername(refinedToken);
    Customer customer =  customerRepository.findByEmail(email)
        .orElseThrow(() -> new CustomerException(CustomerErrorCode.NOT_EXIST_MEMBER));

    // 1-1. member`s cash check
    if (customer.getBalanceCash() < form.getProductPrice()) {
      throw new OrderException(OrderErrorCode.NOT_ENOUGH_CASH);
    }

    //2. cash use
    customerCashService.changeCashBalance(customer, ChangeCashForm.builder()
        .cash(-form.getProductPrice())
        .description("ORDER")
        .fromWhom(email)
        .build());

    //3. service order create
    ServiceOrder serviceOrder = customerOrderService.createOrder(form);

    //4. 추가할 비즈니스 로직들 (ex: 알림 전송, 서비스 상담 data 기록 등 시간 내 구현할 수 있는 추가 사항들 더하기)

    return serviceOrder;
  }

}
