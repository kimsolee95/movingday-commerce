package com.moving.shop.order.controller;

import static com.moving.shop.common.security.JwtAuthenticationFilter.TOKEN_HEADER;
import static com.moving.shop.common.security.JwtAuthenticationFilter.TOKEN_PREFIX;

import com.moving.shop.order.application.OrderApplication;
import com.moving.shop.order.domain.dto.AddOrderProductForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer/order")
public class CustomerOrderController {

  private final OrderApplication orderApplication;

  @PostMapping
  @PreAuthorize("hasAuthority('CUSTOMER')")
  public ResponseEntity<?> takeOrder(@RequestHeader(value = TOKEN_HEADER) String token,
      @RequestBody AddOrderProductForm form) {

    String refinedToken = token.substring(TOKEN_PREFIX.length());
    orderApplication.takeOrder(refinedToken, form);

    return ResponseEntity.ok("");
  }

  @GetMapping("/completion/verify")
  @PreAuthorize("hasAuthority('CUSTOMER')")
  public ResponseEntity<?> customerVerifyOrderCompletion(String code, Long completionOrderId) {
    
    orderApplication.customerOrderCompletionVerify(code, completionOrderId);
    return ResponseEntity.ok("고객님의 주문 완료를 확인하였습니다.");
  }
}
