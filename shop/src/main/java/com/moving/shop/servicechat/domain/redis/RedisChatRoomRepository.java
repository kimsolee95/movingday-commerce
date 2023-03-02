package com.moving.shop.servicechat.domain.redis;

import com.moving.shop.servicechat.domain.entity.ChatRoom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RedisChatRoomRepository {

  //채팅창에 입력한 메시지를 처리하는 리스너
  private final RedisMessageListenerContainer redisMessageListener;
  //구독 서비스
  private final RedisSubscriber redisSubscriber;
  //Redis
  private static final String CHAT_ROOMS = "CHAT_ROOM";
  private final RedisTemplate<String, Object> redisTemplate;
  private HashOperations<String, String, RedisChatRoom> opsHashChatRoom;
  private Map<String, ChannelTopic> topicMap; //key: 채팅방 id

  @PostConstruct
  private void init() {
    opsHashChatRoom = redisTemplate.opsForHash();
    topicMap = new HashMap<>();
  }

  public List<RedisChatRoom> findAllRoom() {
    return opsHashChatRoom.values(CHAT_ROOMS);
  }

  public RedisChatRoom findRoomById(String id) {
    return opsHashChatRoom.get(CHAT_ROOMS, id);
  }

  public ChatRoom createChatRoom(ChatRoom chatRoom) {
    // redis hash에 채팅방 정보를 저장한다.
    //업체 측에서 상품 등록 시, redis hash값도 저장하도록 한다.
    RedisChatRoom redisChatRoom = RedisChatRoom.builder()
        .id(chatRoom.getId())
        .companyId(chatRoom.getCompanyId())
        .customerId(chatRoom.getCustomerId())
        .name(chatRoom.getName())
        .build();
    opsHashChatRoom.put(CHAT_ROOMS, String.valueOf(chatRoom.getId()), redisChatRoom);
    return chatRoom;
  }

  public void enterChatRoom(String roomId) {
    //redis topic을 생성하고, 그 토픽을 리스너가 감시하게 한다.
    ChannelTopic topic = topicMap.get(roomId);
    if (topic == null) {
      topic = new ChannelTopic(roomId);
      redisMessageListener.addMessageListener(redisSubscriber, topic);
      topicMap.put(roomId, topic);
    }
  }

  public ChannelTopic getTopic(String roomId) {
    return topicMap.get(roomId);
  }

}
