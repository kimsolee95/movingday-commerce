package com.moving.shop.servicechat.domain.redis;

import com.moving.shop.servicechat.domain.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisPublisher {

  private final RedisTemplate<String, Object> redisTemplate;

  public void publish(ChannelTopic topic, ChatMessage message) {
    //메시지 입력 시, Redis Topic에 해당 메시지를 발행하는 서비스.
    redisTemplate.convertAndSend(topic.getTopic(), message);
  }

}
