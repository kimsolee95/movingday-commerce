package com.moving.shop.product.controller;

import static com.moving.shop.common.security.JwtAuthenticationFilter.TOKEN_HEADER;
import static com.moving.shop.common.security.JwtAuthenticationFilter.TOKEN_PREFIX;

import com.moving.shop.product.domain.dto.AddServiceProductForm;
import com.moving.shop.product.domain.dto.ServiceProductResponse;
import com.moving.shop.product.domain.dto.UpdateServiceProductForm;
import com.moving.shop.product.service.CompanyProductService;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
  @ApiOperation(value="업체 회원의 서비스 상품 추가 API", notes = "로그인한 업체 회원이 고객의 요청서에 대한 서비스 상품을 작성할 때 사용합니다.")
  public ResponseEntity<ServiceProductResponse> addServiceProduct(@RequestHeader(value = TOKEN_HEADER) String token,
      @Valid @RequestBody AddServiceProductForm form) {

    String refinedToken = token.substring(TOKEN_PREFIX.length());
    return ResponseEntity.ok(
        ServiceProductResponse.from(companyProductService.addServiceProduct(refinedToken, form)));
  }

  @PutMapping
  @PreAuthorize("hasAuthority('COMPANY')")
  @ApiOperation(value="업체 회원의 서비스 상품 수정 API", notes = "로그인한 업체 회원이 고객의 요청서에 대한 서비스 상품(주문이 들어가기 전의 경우)을 수정할 때 사용합니다.")
  public ResponseEntity<?> updateServiceProduct(@RequestHeader(value = TOKEN_HEADER) String token,
      @Valid @RequestBody UpdateServiceProductForm form) {

    String refinedToken = token.substring(TOKEN_PREFIX.length());
    return ResponseEntity.ok(
        ServiceProductResponse.from(companyProductService.updateServiceProduct(refinedToken, form)));
  }

  @DeleteMapping("/{serviceProductId}")
  @PreAuthorize("hasAuthority('COMPANY')")
  @ApiOperation(value="업체 회원의 서비스 상품 삭제 API", notes = "로그인한 업체 회원이 고객의 요청서에 대한 서비스 상품(주문이 들어가기 전의 경우)을 삭제할 때 사용합니다.")
  public ResponseEntity<?> deleteServiceProduct(@RequestHeader(value = TOKEN_HEADER) String token,
      @PathVariable("serviceProductId") Long serviceProductId) {

    String refinedToken = token.substring(TOKEN_PREFIX.length());
    companyProductService.deleteServiceProduct(refinedToken, serviceProductId);
    return ResponseEntity.ok("");
  }

}
