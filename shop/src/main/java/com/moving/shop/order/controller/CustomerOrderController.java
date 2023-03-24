package com.moving.shop.order.controller;

import static com.moving.shop.common.security.JwtAuthenticationFilter.TOKEN_HEADER;
import static com.moving.shop.common.security.JwtAuthenticationFilter.TOKEN_PREFIX;

import com.moving.shop.order.application.OrderApplication;
import com.moving.shop.order.domain.dto.AddOrderProductForm;
import io.swagger.annotations.ApiOperation;
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
  @ApiOperation(value="고객 회원의 서비스 상품 주문 API", notes = "로그인한 고객 회원이 업체 측에서 제시한 상품을 주문 시에 사용합니다.")
  public ResponseEntity<?> takeOrder(@RequestHeader(value = TOKEN_HEADER) String token,
      @RequestBody AddOrderProductForm form) {

    String refinedToken = token.substring(TOKEN_PREFIX.length());
    orderApplication.takeOrder(refinedToken, form);

    return ResponseEntity.ok("");
  }

  @GetMapping("/completion/verify")
  @PreAuthorize("hasAuthority('CUSTOMER')")
  @ApiOperation(value="고객 회원의 서비스 상품 시행 완료 API", notes = "로그인한 고객 회원이 주문된 서비스를 받은 후, 업체 측으로부터의 요청을 받아 완료 처리를 할 때 사용합니다.")
  public ResponseEntity<?> customerVerifyOrderCompletion(String code, Long completionOrderId) {

    orderApplication.customerOrderCompletionVerify(code, completionOrderId);
    return ResponseEntity.ok("고객님의 주문 완료를 확인하였습니다.");
  }
}
