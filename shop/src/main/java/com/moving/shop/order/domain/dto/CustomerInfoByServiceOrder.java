package com.moving.shop.order.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class CustomerInfoByServiceOrder {

  private Long customerId;

  private String phone;

  @QueryProjection
  public CustomerInfoByServiceOrder(Long customerId, String phone) {
    this.customerId = customerId;
    this.phone = phone;
  }

}
