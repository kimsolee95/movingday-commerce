package com.moving.shop.order.service;

import com.moving.shop.order.domain.dto.SubmittedOrders;
import java.util.List;

public interface CompanyOrderService {

  List<SubmittedOrders> findSubmittedOrderProductsByCompanyId(String refinedToken);

}
