package com.moving.shop.company.controller;

import static com.moving.shop.common.security.JwtAuthenticationFilter.TOKEN_HEADER;
import static com.moving.shop.common.security.JwtAuthenticationFilter.TOKEN_PREFIX;

import com.moving.shop.company.domain.dto.CompanyInfo;
import com.moving.shop.company.service.CompanyService;
import com.moving.shop.customer.service.CustomerRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
public class CompanyController {

  private final CompanyService companyService;
  private final CustomerRequestService customerRequestService;

  @GetMapping
  @PreAuthorize("hasAuthority('COMPANY')")
  public ResponseEntity<CompanyInfo> getCompanyInfo(@RequestHeader(value = TOKEN_HEADER) String token) {

    String refinedToken = token.substring(TOKEN_PREFIX.length());
    return ResponseEntity.ok(companyService.getCompanyInfo(refinedToken));
  }

  @GetMapping("/customer-requests")
  @PreAuthorize("hasAuthority('COMPANY')")
  public ResponseEntity<?> getCustomerRequests(@RequestHeader(value = TOKEN_HEADER) String token) {
    return ResponseEntity.ok(customerRequestService.getCustomerRequests());
  }

}
