package com.moving.shop.product.controller;

import static com.moving.shop.common.security.JwtAuthenticationFilter.TOKEN_HEADER;
import static com.moving.shop.common.security.JwtAuthenticationFilter.TOKEN_PREFIX;

import com.moving.shop.product.domain.dto.ProposedProductResponse;
import com.moving.shop.product.service.CustomerProductService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  @ApiOperation(value="고객 회원의 제안받은 서비스 상품 확인 API", notes = "로그인한 고객 회원이 작성한 견적 요청서에 대해 업체 측으로부터 받은 서비스 상품들을 확인 시 사용합니다.")
  public ResponseEntity<?> showReceivedServiceProduct(@RequestHeader(value = TOKEN_HEADER) String token) {

    String refinedToken = token.substring(TOKEN_PREFIX.length());
    return ResponseEntity.ok(ProposedProductResponse.from(customerProductService.findByCustomerId(refinedToken)));
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('CUSTOMER')")
  @ApiOperation(value="고객 회원의 제안받은 서비스 상품 확인 API", notes = "로그인한 고객 회원이 작성한 견적 요청서에 대한 업체측으로부터 받은 서비스 상품 상세 정보를 확인 시 사용합니다. (상품에 대한 상품옵션 같이 조회)")
  public ResponseEntity<?> getCustomersReceivedProduct(@PathVariable("id")Long serviceProductId) {
    return ResponseEntity.ok(customerProductService.findWithProductOptionsById(serviceProductId));
  }

}
