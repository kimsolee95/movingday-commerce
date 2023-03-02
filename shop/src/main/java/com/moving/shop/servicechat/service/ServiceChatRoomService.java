package com.moving.shop.servicechat.service;

import com.moving.shop.servicechat.domain.entity.ChatRoom;
import java.util.List;

public interface ServiceChatRoomService {

  List<ChatRoom> findCustomersChatRoomList(String refinedToken);

  List<ChatRoom> findCompaniesChatRoomList(String refinedToken);

}
