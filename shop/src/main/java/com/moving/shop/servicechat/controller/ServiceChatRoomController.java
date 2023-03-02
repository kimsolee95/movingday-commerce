package com.moving.shop.servicechat.controller;

import static com.moving.shop.common.security.JwtAuthenticationFilter.TOKEN_HEADER;
import static com.moving.shop.common.security.JwtAuthenticationFilter.TOKEN_PREFIX;

import com.moving.shop.servicechat.service.ServiceChatRoomService;
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
  public ResponseEntity<?> chatRoomsForCustomer(@RequestHeader(value = TOKEN_HEADER) String token) {

    String refinedToken = token.substring(TOKEN_PREFIX.length());
    return ResponseEntity.ok(serviceChatRoomService.findCustomersChatRoomList(refinedToken));
  }

  @GetMapping("/rooms/company")
  @PreAuthorize("hasAuthority('COMPANY')")
  public ResponseEntity<?> chatRoomsForCompany(@RequestHeader(value = TOKEN_HEADER) String token) {

    String refinedToken = token.substring(TOKEN_PREFIX.length());
    return ResponseEntity.ok(serviceChatRoomService.findCompaniesChatRoomList(refinedToken));
  }

}
