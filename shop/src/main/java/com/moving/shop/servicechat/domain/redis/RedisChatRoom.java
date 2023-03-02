package com.moving.shop.servicechat.domain.redis;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RedisChatRoom implements Serializable {

  private static final long serialVersionUID = 6494678977089006639L;

  private Long id;
  private String name;
  private Long customerId;
  private Long companyId;
}
