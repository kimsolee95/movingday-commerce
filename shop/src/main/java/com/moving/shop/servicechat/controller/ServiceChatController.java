package com.moving.shop.servicechat.controller;

import com.moving.shop.servicechat.domain.dto.ChatMessage;
import com.moving.shop.servicechat.domain.redis.RedisChatRoomRepository;
import com.moving.shop.servicechat.domain.redis.RedisPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class ServiceChatController {

  //private final SimpMessagingTemplate simpMessagingTemplate;

  private final RedisPublisher redisPublisher;
  private final RedisChatRoomRepository redisChatRoomRepository;

  @MessageMapping(value = "/chat/enter-room")
  public void enter(ChatMessage chatMessage) {

//    chatMessage.setMessage(chatMessage.getWriter() + "님이 서비스상담에 참여하였습니다.");
//    simpMessagingTemplate.convertAndSend("/sub/chat/room/" + chatMessage.getRoomId(), chatMessage);

    //redis topic을 생성하고, 그 토픽을 리스너가 감시하게 한다.
    if ("ENTER".equals(chatMessage.getType())) {
      redisChatRoomRepository.enterChatRoom(chatMessage.getRoomId());
    }
  }

  @MessageMapping(value = "/chat/message")
  public void message(ChatMessage chatMessage) {

    //simpMessagingTemplate.convertAndSend("/sub/chat/room/" + chatMessage.getRoomId(), chatMessage);
    redisPublisher.publish(redisChatRoomRepository.getTopic(chatMessage.getRoomId()), chatMessage);
  }

}
