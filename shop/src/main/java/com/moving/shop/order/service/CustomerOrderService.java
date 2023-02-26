package com.moving.shop.order.service;

import com.moving.shop.order.domain.dto.AddOrderProductForm;
import com.moving.shop.order.domain.entity.ServiceOrder;

public interface CustomerOrderService {

  ServiceOrder createOrder(AddOrderProductForm form);

}
