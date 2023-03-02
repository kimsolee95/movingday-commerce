package com.moving.shop.servicechat.domain.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moving.shop.servicechat.domain.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber implements MessageListener {

  private final ObjectMapper objectMapper;
  private final RedisTemplate redisTemplate;
  private final SimpMessageSendingOperations messageSendingOperations;


  @Override
  public void onMessage(Message message, byte[] pattern) {

    try {

      //redis에서 publish한 데이터를 deserialize
      String publishMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
      //ChatMessage로 변환
      ChatMessage chatMessage = objectMapper.readValue(publishMessage, ChatMessage.class);
      //subscriber에게 ChatMessage 보내기
      messageSendingOperations.convertAndSend("/sub/chat/room/" + chatMessage.getRoomId(), chatMessage);
    } catch (Exception e) {
      log.error(e.getMessage());
    }

  }
}
