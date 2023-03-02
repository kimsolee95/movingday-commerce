package com.moving.shop.servicechat.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moving.shop.product.domain.entity.ServiceProduct;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private Long customerId;

  private Long companyId;

  @JsonIgnore //채팅방 리스트 select 시, 이 부분은 json ignore 처리
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "service_product_id")
  private ServiceProduct serviceProduct;

  public static ChatRoom create(ServiceProduct serviceProduct, String name, Long customerId, Long companyId) {

    return ChatRoom.builder()
        .name(name)
        .customerId(customerId)
        .companyId(companyId)
        .serviceProduct(serviceProduct)
        .build();
  }

}
