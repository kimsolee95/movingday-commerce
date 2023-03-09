package com.moving.shop.common.client.sms;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsRequest {

  String type;
  String contentType;
  String countryCode;
  String from;
  String content;
  List<SmsMessageForm> messages;
}
