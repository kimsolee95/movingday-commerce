package com.moving.shop.common.client.sms;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsResponse {

  String requestId;
  LocalDateTime requestTime;
  String statusCode;
  String statusName;
}
