package com.moving.shop.product.controller;

import static com.moving.shop.common.security.JwtAuthenticationFilter.TOKEN_HEADER;
import static com.moving.shop.common.security.JwtAuthenticationFilter.TOKEN_PREFIX;

import com.moving.shop.product.domain.dto.AddServiceProductForm;
import com.moving.shop.product.domain.dto.ServiceProductResponse;
import com.moving.shop.product.service.CompanyProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/company/service-product")
@RequiredArgsConstructor
public class CompanyProductController {

  private final CompanyProductService companyProductService;

  @PostMapping
  @PreAuthorize("hasAuthority('COMPANY')")
  public ResponseEntity<ServiceProductResponse> addServiceProduct(@RequestHeader(value = TOKEN_HEADER) String token,
      @RequestBody AddServiceProductForm form) {

    String refinedToken = token.substring(TOKEN_PREFIX.length());
    return ResponseEntity.ok(
        ServiceProductResponse.from(companyProductService.addServiceProduct(refinedToken, form)));
  }

}
