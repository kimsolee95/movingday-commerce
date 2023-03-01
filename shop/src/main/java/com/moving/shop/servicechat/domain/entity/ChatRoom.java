package com.moving.shop.servicechat.domain.entity;

import com.moving.shop.product.domain.entity.ServiceProduct;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
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
public class ChatRoom implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String id;

  private static final long serialVersionUID = 6494678977089006639L;

  private String name;

  private Long customerId;

  private Long companyId;

  @OneToOne
  @JoinColumn(name = "service_product_id")
  private ServiceProduct serviceProduct;

  public static ChatRoom create(String name, Long customerId, Long companyId) {

    return ChatRoom.builder()
        .id(UUID.randomUUID().toString())
        .name(name)
        .customerId(customerId)
        .companyId(companyId)
        .build();
  }

}
