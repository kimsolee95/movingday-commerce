package com.moving.shop.order.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.moving.shop.common.client.sms.SmsMessageForm;
import com.moving.shop.common.exception.customexception.CompanyException;
import com.moving.shop.common.exception.customexception.OrderException;
import com.moving.shop.common.exception.type.CompanyErrorCode;
import com.moving.shop.common.exception.type.OrderErrorCode;
import com.moving.shop.common.security.TokenProvider;
import com.moving.shop.common.service.SmsService;
import com.moving.shop.company.domain.entity.Company;
import com.moving.shop.company.domain.repository.CompanyRepository;
import com.moving.shop.order.domain.dto.CompleteOrderForm;
import com.moving.shop.order.domain.dto.CustomerInfoByServiceOrder;
import com.moving.shop.order.domain.dto.SubmittedOrders;
import com.moving.shop.order.domain.entity.CompletionOrder;
import com.moving.shop.order.domain.entity.OrderProduct;
import com.moving.shop.order.domain.entity.ServiceOrder;
import com.moving.shop.order.domain.repository.CompletionOrderRepository;
import com.moving.shop.order.domain.repository.OrderProductRepository;
import com.moving.shop.order.domain.repository.ServiceOrderRepository;
import com.moving.shop.order.domain.type.OrderStatus;
import com.moving.shop.order.service.CompanyOrderService;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyOrderServiceImpl implements CompanyOrderService {

  private final OrderProductRepository orderProductRepository;
  private final CompanyRepository companyRepository;
  private final CompletionOrderRepository completionOrderRepository;
  private final ServiceOrderRepository serviceOrderRepository;
  private final TokenProvider tokenProvider;
  private final SmsService smsService;

  @Override
  public List<SubmittedOrders> findSubmittedOrderProductsByCompanyId(String refinedToken) {

    String email = tokenProvider.getUsername(refinedToken);
    Company company = companyRepository.findByEmail(email)
        .orElseThrow(() -> new CompanyException(CompanyErrorCode.NOT_EXIST_COMPANY_MEMBER));

    return orderProductRepository.findByCompanyId(company.getId());
  }

  @Transactional
  @Override
  public CompletionOrder completeServiceOrder(String refinedToken, CompleteOrderForm form) {

    //1. 고객 제외 확인으로 data save
    //order info
    ServiceOrder serviceOrder = serviceOrderRepository.findById(form.getServiceOrderId())
            .orElseThrow(() -> new OrderException(OrderErrorCode.NOT_COMPLETE_ORDER));

    //order product info
    OrderProduct orderProduct = orderProductRepository.findByServiceOrder_Id(form.getServiceOrderId())
            .orElseThrow(() -> new OrderException(OrderErrorCode.NOT_COMPLETE_ORDER));

    //order complete data create
    CompletionOrder completionOrder = completionOrderRepository.save(CompletionOrder.builder()
            .serviceOrder(serviceOrder)
            .companyCheckYn(true)
            .customerCheckYn(false)
            .orderPrice(orderProduct.getOrderPrice())
            .verificationCode(UUID.randomUUID().toString())
        .build());

    //service order status update
    serviceOrder.completeServiceOrder(OrderStatus.COMPLETE);

    //2. 해당 고객에게 message 확인 발송
    //find customer
    CustomerInfoByServiceOrder customerInfo = serviceOrderRepository.getCustomerInfoByServiceOrderId(serviceOrder.getId());

    try {
      smsService.sendSms(
          SmsMessageForm.builder()
          .content(
              "[서비스 완료]\n 주문하신 서비스가 완료되었음을 아래 링크를 클릭하여 확인해주세요\n" +
              "주문완료 확인 링크>> http:http://localhost:8081/api/customer/order/completion/verify" +
              "?" + "code=" + completionOrder.getVerificationCode() +
              "&id=" + completionOrder.getId()
          )
          .to(customerInfo.getPhone()) //customer`s phone
          .build());
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    } catch (InvalidKeyException e) {
      e.printStackTrace();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    return completionOrder;
  }
}
