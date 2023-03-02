package com.moving.shop.servicechat.controller;

import com.moving.shop.servicechat.domain.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ServiceChatRoomController {

  private final ChatRoomRepository chatRoomRepository;

  @GetMapping("/rooms")
  @CrossOrigin
  public ResponseEntity<?> chatRooms() {
    //모든 채팅방 목록
    return ResponseEntity.ok(chatRoomRepository.findAll());
  }

}
