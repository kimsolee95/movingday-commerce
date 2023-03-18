package com.moving.shop.order.service.impl;

import com.moving.shop.common.exception.customexception.OrderException;
import com.moving.shop.common.exception.type.OrderErrorCode;
import com.moving.shop.customer.domain.entity.CustomerRequest;
import com.moving.shop.customer.domain.repository.CustomerRequestRepository;
import com.moving.shop.order.domain.dto.AddOrderProductForm;
import com.moving.shop.order.domain.dto.AddOrderProductOptionForm;
import com.moving.shop.order.domain.entity.CompletionOrder;
import com.moving.shop.order.domain.entity.OrderProduct;
import com.moving.shop.order.domain.entity.ServiceOrder;
import com.moving.shop.order.domain.repository.CompletionOrderRepository;
import com.moving.shop.order.domain.repository.OrderProductRepository;
import com.moving.shop.order.domain.repository.ServiceOrderRepository;
import com.moving.shop.order.service.CustomerOrderService;
import com.moving.shop.product.domain.entity.ProductOption;
import com.moving.shop.product.domain.entity.ServiceProduct;
import com.moving.shop.product.domain.repository.ProductOptionRepository;
import com.moving.shop.product.domain.repository.ServiceProductRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerOrderServiceImpl implements CustomerOrderService {

  private final CompletionOrderRepository completionOrderRepository;
  private final OrderProductRepository orderProductRepository;
  private final ServiceProductRepository serviceProductRepository;
  private final ServiceOrderRepository serviceOrderRepository;
  private final CustomerRequestRepository customerRequestRepository;
  private final ProductOptionRepository productOptionRepository;

  @Transactional
  @Override
  public ServiceOrder createOrder(AddOrderProductForm form) {

    // 2-1. customerRequest data check (고객 서비스요청서 id가 실제 존재하는 id인지 확인)
    CustomerRequest customerRequest = customerRequestRepository.findById(form.getCustomerRequestId())
        .orElseThrow(() -> new OrderException(OrderErrorCode.NOT_VALID_REQUEST_INFO));

    // 2-2. oneToOne relation check
    // customerRequest id check (service order table 내에 이 서비스 요청서 id가 존재한다면 불가능)
    boolean isAlreadyOrdered = serviceOrderRepository.existsByCustomerRequestId(customerRequest.getId());
    if (isAlreadyOrdered) {
      throw new OrderException(OrderErrorCode.ALREADY_TAKE_ORDER);
    }

    // 2-3. product check (refactoring 시, findById 대신 graphEntity 활용 필요)
    ServiceProduct serviceProduct = serviceProductRepository.findById(form.getServiceProductId())
            .orElseThrow(() -> new OrderException(OrderErrorCode.NOT_VALID_PRODUCT_INFO));

    // 2-4. option list check
    List<ProductOption> wishOptions = makeValidatedWishOptions(form.getAddOrderProductOptionForms());

    // 2-5. save serviceOrder
    ServiceOrder serviceOrder = serviceOrderRepository.save(ServiceOrder.createServiceOrder(customerRequest));

    // 2-6. save orderProduct
    serviceProduct.buyServiceProduct(serviceProduct); //set 사용하지 않는 방식으로 수정
    OrderProduct orderProduct = orderProductRepository.save(
        OrderProduct.of(serviceOrder, serviceProduct, form, wishOptions));

    return serviceOrder;
  }

  @Transactional
  @Override
  public void orderCompletionVerify(String code, Long completionOrderId) {

    CompletionOrder completionOrder = completionOrderRepository.findByIdAndVerificationCode(completionOrderId, code)
        .orElseThrow(() -> new OrderException(OrderErrorCode.NOT_VALID_REQUEST_INFO));

    if (!completionOrder.isCompanyCheckYn()) {
      throw new OrderException(OrderErrorCode.NOT_MATCHED_ORDER_INFO);
    } else if (completionOrder.isCustomerCheckYn()) {
      throw new OrderException(OrderErrorCode.NOT_MATCHED_ORDER_INFO);
    }

    completionOrder.customersVerify();
  }

  private List<ProductOption> makeValidatedWishOptions(List<AddOrderProductOptionForm> addOrderProductOptionForms) {

    List<ProductOption> wishOptions = new ArrayList<>();

    for (AddOrderProductOptionForm option : addOrderProductOptionForms) {

      ProductOption productOption = productOptionRepository.findById(option.getProductOptionId())
          .orElseThrow(() -> new OrderException(OrderErrorCode.NOT_VALID_PRODUCT_INFO));

      wishOptions.add(productOption);
      ProductOption.buyOption(productOption);
    }

    return wishOptions;
  }

}
