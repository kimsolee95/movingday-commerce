package com.moving.shop.order.domain.repository;

import com.moving.shop.order.domain.dto.CustomerInfoByServiceOrder;

public interface ServiceOrderRepositoryCustom {

  CustomerInfoByServiceOrder getCustomerInfoByServiceOrderId(Long serviceOrderId);

}
