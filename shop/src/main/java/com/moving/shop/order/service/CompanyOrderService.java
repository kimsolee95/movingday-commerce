package com.moving.shop.order.service;

import com.moving.shop.order.domain.dto.CompleteOrderForm;
import com.moving.shop.order.domain.dto.SubmittedOrders;
import com.moving.shop.order.domain.entity.CompletionOrder;
import java.util.List;

public interface CompanyOrderService {

  List<SubmittedOrders> findSubmittedOrderProductsByCompanyId(String refinedToken);

  CompletionOrder completeServiceOrder(String refinedToken, CompleteOrderForm form);
}
