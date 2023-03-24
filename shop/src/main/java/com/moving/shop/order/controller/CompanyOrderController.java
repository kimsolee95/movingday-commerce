package com.moving.shop.order.controller;

import static com.moving.shop.common.security.JwtAuthenticationFilter.TOKEN_HEADER;
import static com.moving.shop.common.security.JwtAuthenticationFilter.TOKEN_PREFIX;

import com.moving.shop.order.domain.dto.CompleteOrderForm;
import com.moving.shop.order.domain.dto.SubmittedOrders;
import com.moving.shop.order.service.CompanyOrderService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
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
@RequestMapping("/api/company/service-orders")
public class CompanyOrderController {

  private final CompanyOrderService companyOrderService;

  @PostMapping("/completion")
  @PreAuthorize("hasAuthority('COMPANY')")
  @ApiOperation(value="업체 회원의 서비스 완료 API", notes = "로그인한 업체 회원이 주문된 서비스 시행 후, 완료 처리를 할 때 사용합니다.")
  public ResponseEntity<?> completeOrder(@RequestHeader(value = TOKEN_HEADER) String token,
      @RequestBody CompleteOrderForm form) {

    String refinedToken = token.substring(TOKEN_PREFIX.length());
    companyOrderService.completeServiceOrder(refinedToken, form);
    return ResponseEntity.ok("업체회원님의 주문완료 확인을 완료하였습니다. 상품 주문 고객님의 문자 알림 확인 시, 주문완료 확인이 정상적으로 처리됩니다.");
  }

  @GetMapping
  @PreAuthorize("hasAuthority('COMPANY')")
  @ApiOperation(value="업체 회원의 주문접수된 상품내역 확인 API", notes = "로그인한 업체 회원이 주문접수 상태에 있는 서비스 상품을 확인합니다.")
  public ResponseEntity<List<SubmittedOrders>> showSubmittedOrders(@RequestHeader(value = TOKEN_HEADER) String token) {

    String refinedToken = token.substring(TOKEN_PREFIX.length());
    return ResponseEntity.ok(companyOrderService.findSubmittedOrderProductsByCompanyId(refinedToken));
  }


}
