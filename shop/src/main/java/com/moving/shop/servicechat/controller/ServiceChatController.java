package com.moving.shop.servicechat.controller;

import com.moving.shop.servicechat.domain.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class ServiceChatController {

  private final SimpMessagingTemplate simpMessagingTemplate;

  @MessageMapping(value = "/chat/enter")
  public void enter(ChatMessage chatMessage) {

    chatMessage.setMessage(chatMessage.getWriter() + "님이 서비스상담에 참여하였습니다.");
    simpMessagingTemplate.convertAndSend("/sub/chat/room/" + chatMessage.getRoomId(), chatMessage);
  }

  @MessageMapping(value = "/chat/message")
  public void message(ChatMessage chatMessage) {

    simpMessagingTemplate.convertAndSend("/sub/chat/room/" + chatMessage.getRoomId(), chatMessage);
  }

}
