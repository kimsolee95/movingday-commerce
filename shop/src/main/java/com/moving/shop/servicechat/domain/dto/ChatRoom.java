package com.moving.shop.servicechat.domain.dto;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoom { //for test class

  private String roomId;
  private String name;
//  private Set<WebSocketSession> sessions = new HashSet<>();
  //spring WebSocket Connection

  public static ChatRoom create(String name) {

    ChatRoom chatRoom = new ChatRoom();
    chatRoom.roomId = UUID.randomUUID().toString();
    chatRoom.name = name;
    return chatRoom;
  }

}
