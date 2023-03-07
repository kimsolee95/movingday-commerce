package com.moving.shop.order.controller;

import static com.moving.shop.common.security.JwtAuthenticationFilter.TOKEN_HEADER;
import static com.moving.shop.common.security.JwtAuthenticationFilter.TOKEN_PREFIX;

import com.moving.shop.order.domain.dto.SubmittedOrders;
import com.moving.shop.order.service.CompanyOrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
  public ResponseEntity<?> completeOrder() {

    return ResponseEntity.ok("");
  }

  @GetMapping
  @PreAuthorize("hasAuthority('COMPANY')")
  public ResponseEntity<List<SubmittedOrders>> showSubmittedOrders(@RequestHeader(value = TOKEN_HEADER) String token) {

    String refinedToken = token.substring(TOKEN_PREFIX.length());
    return ResponseEntity.ok(companyOrderService.findSubmittedOrderProductsByCompanyId(refinedToken));
  }


}
