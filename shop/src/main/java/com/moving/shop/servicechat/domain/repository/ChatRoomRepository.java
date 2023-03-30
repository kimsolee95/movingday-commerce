package com.moving.shop.servicechat.domain.repository;

import com.moving.shop.servicechat.domain.entity.ChatRoom;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

  List<ChatRoom> findAll();

  List<ChatRoom> findAllByCustomerId(Long customerId);

  List<ChatRoom> findAllByCompanyId(Long companyId);

  Optional<ChatRoom> findByServiceProduct_Id(@RequestParam("service_product_id") Long serviceProductId);

  boolean existsById(Long id);
}
