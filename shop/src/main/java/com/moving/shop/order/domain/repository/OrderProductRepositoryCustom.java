package com.moving.shop.order.domain.repository;

import com.moving.shop.order.domain.dto.SubmittedOrders;
import java.util.List;

public interface OrderProductRepositoryCustom {

  List<SubmittedOrders> findByCompanyId(Long companyId);

}
