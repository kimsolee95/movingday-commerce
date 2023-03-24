package com.moving.shop.servicechat.controller;

import static com.moving.shop.common.security.JwtAuthenticationFilter.TOKEN_HEADER;
import static com.moving.shop.common.security.JwtAuthenticationFilter.TOKEN_PREFIX;

import com.moving.shop.servicechat.service.ServiceChatRoomService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ServiceChatRoomController {

  private final ServiceChatRoomService serviceChatRoomService;

  @GetMapping("/rooms/customer")
  @PreAuthorize("hasAuthority('CUSTOMER')")
  @ApiOperation(value="고객 회원의 제안받은 서비스 상품에 대한 업체와의 1:1 채팅방 목록", notes = "로그인한 고객 회원이 작성한 견적 요청서에 대해 업체 측으로부터 제안받은 서비스 상품에 대해 문의할 수 있는 1:1 채팅방 목록입니다.")
  public ResponseEntity<?> chatRoomsForCustomer(@RequestHeader(value = TOKEN_HEADER) String token) {

    String refinedToken = token.substring(TOKEN_PREFIX.length());
    return ResponseEntity.ok(serviceChatRoomService.findCustomersChatRoomList(refinedToken));
  }

  @GetMapping("/rooms/company")
  @PreAuthorize("hasAuthority('COMPANY')")
  @ApiOperation(value="업체 회원의 제안한 서비스 상품에 대한 고객과의 1:1 채팅방 목록", notes = "로그인한 업체 회원이 고객 측으로 제안한 서비스 상품에 대해 문의 대응할 수 있는 1:1 채팅방 목록입니다.")
  public ResponseEntity<?> chatRoomsForCompany(@RequestHeader(value = TOKEN_HEADER) String token) {

    String refinedToken = token.substring(TOKEN_PREFIX.length());
    return ResponseEntity.ok(serviceChatRoomService.findCompaniesChatRoomList(refinedToken));
  }

}
