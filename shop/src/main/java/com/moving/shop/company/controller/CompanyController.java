package com.moving.shop.company.controller;

import static com.moving.shop.common.security.JwtAuthenticationFilter.TOKEN_HEADER;
import static com.moving.shop.common.security.JwtAuthenticationFilter.TOKEN_PREFIX;

import com.moving.shop.company.domain.dto.CompanyInfo;
import com.moving.shop.company.service.CompanyService;
import com.moving.shop.customer.service.CustomerRequestService;
import io.swagger.annotations.ApiOperation;
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
  @ApiOperation(value="업체 정보 확인 API", notes = "로그인한 업체가 자신의 정보를 확인합니다.")
  public ResponseEntity<CompanyInfo> getCompanyInfo(@RequestHeader(value = TOKEN_HEADER) String token) {

    String refinedToken = token.substring(TOKEN_PREFIX.length());
    return ResponseEntity.ok(companyService.getCompanyInfo(refinedToken));
  }

  @GetMapping("/customer-requests")
  @PreAuthorize("hasAuthority('COMPANY')")
  @ApiOperation(value="고객 서비스 요청서 전체조회 API", notes = "로그인한 업체가 고객의 서비스 요청서를 전체조회합니다.")
  public ResponseEntity<?> getCustomerRequests(@RequestHeader(value = TOKEN_HEADER) String token) {
    return ResponseEntity.ok(customerRequestService.getCustomerRequests());
  }

}
