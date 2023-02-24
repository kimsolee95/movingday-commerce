package com.moving.shop.product.controller;

import static com.moving.shop.common.security.JwtAuthenticationFilter.TOKEN_HEADER;
import static com.moving.shop.common.security.JwtAuthenticationFilter.TOKEN_PREFIX;

import com.moving.shop.product.domain.dto.ProposedProductResponse;
import com.moving.shop.product.service.CustomerProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer/service-product")
@RequiredArgsConstructor
public class CustomerProductController {

  private final CustomerProductService customerProductService;

  @GetMapping
  @PreAuthorize("hasAuthority('CUSTOMER')")
  public ResponseEntity<?> showReceivedServiceProduct(@RequestHeader(value = TOKEN_HEADER) String token) {

    String refinedToken = token.substring(TOKEN_PREFIX.length());
    return ResponseEntity.ok(ProposedProductResponse.from(customerProductService.findByCustomerId(refinedToken)));
  }

}
