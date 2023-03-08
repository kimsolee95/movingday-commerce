package com.moving.shop.order.service.impl;

import com.moving.shop.common.exception.customexception.CompanyException;
import com.moving.shop.common.exception.customexception.OrderException;
import com.moving.shop.common.exception.type.CompanyErrorCode;
import com.moving.shop.common.exception.type.OrderErrorCode;
import com.moving.shop.common.security.TokenProvider;
import com.moving.shop.company.domain.entity.Company;
import com.moving.shop.company.domain.repository.CompanyRepository;
import com.moving.shop.order.domain.dto.CompleteOrderForm;
import com.moving.shop.order.domain.dto.SubmittedOrders;
import com.moving.shop.order.domain.entity.CompletionOrder;
import com.moving.shop.order.domain.entity.OrderProduct;
import com.moving.shop.order.domain.entity.ServiceOrder;
import com.moving.shop.order.domain.repository.CompletionOrderRepository;
import com.moving.shop.order.domain.repository.OrderProductRepository;
import com.moving.shop.order.domain.repository.ServiceOrderRepository;
import com.moving.shop.order.service.CompanyOrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyOrderServiceImpl implements CompanyOrderService {

  private final OrderProductRepository orderProductRepository;
  private final CompanyRepository companyRepository;
  private final CompletionOrderRepository completionOrderRepository;
  private final ServiceOrderRepository serviceOrderRepository;
  private final TokenProvider tokenProvider;

  @Override
  public List<SubmittedOrders> findSubmittedOrderProductsByCompanyId(String refinedToken) {

    String email = tokenProvider.getUsername(refinedToken);
    Company company = companyRepository.findByEmail(email)
        .orElseThrow(() -> new CompanyException(CompanyErrorCode.NOT_EXIST_COMPANY_MEMBER));

    return orderProductRepository.findByCompanyId(company.getId());
  }

  @Override
  public CompletionOrder completeServiceOrder(String refinedToken, CompleteOrderForm form) {

    //1. 고객 제외 확인으로 data save

    //order info
    ServiceOrder serviceOrder = serviceOrderRepository.findById(form.getServiceOrderId())
            .orElseThrow(() -> new OrderException(OrderErrorCode.NOT_COMPLETE_ORDER));

    //order product info
    OrderProduct orderProduct = orderProductRepository.findByServiceOrder_Id(form.getServiceOrderId())
            .orElseThrow(() -> new OrderException(OrderErrorCode.NOT_COMPLETE_ORDER));

    completionOrderRepository.save(CompletionOrder.builder()
            .serviceOrder(serviceOrder)
            .companyCheckYn(true)
            .customerCheckYn(false)
            .orderPrice(orderProduct.getOrderPrice())
        .build());

    //2. 해당 고객에게 message 확인 발송


    return null;
  }
}
