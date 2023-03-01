package com.moving.shop.servicechat.controller;

import com.moving.shop.servicechat.domain.dto.ChatRoom;
import com.moving.shop.servicechat.domain.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ServiceChatRoomController {

  private final ChatRoomRepository chatRoomRepository;

  @GetMapping("/rooms")
  public ResponseEntity<?> chatRooms() { //test
    //모든 채팅방 목록
    return ResponseEntity.ok(chatRoomRepository.findAllRoom());
  }

  @PostMapping("/room")
  public ResponseEntity<?> createRoom(@RequestParam String name) { //test
    //채팅방 생성
    return ResponseEntity.ok(chatRoomRepository.createChatRoom(name));
  }

  @GetMapping("/room/{roomId}")
  public ResponseEntity<?> getRoomInfo(@PathVariable String roomId) { //test
    //특정 채팅방 조회
    return ResponseEntity.ok(chatRoomRepository.findRoomById(roomId));
  }

}
