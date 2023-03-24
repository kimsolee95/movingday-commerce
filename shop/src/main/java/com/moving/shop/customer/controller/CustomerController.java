package com.moving.shop.customer.controller;

import static com.moving.shop.common.security.JwtAuthenticationFilter.TOKEN_HEADER;
import static com.moving.shop.common.security.JwtAuthenticationFilter.TOKEN_PREFIX;

import com.moving.shop.customer.domain.dto.ChangeCashForm;
import com.moving.shop.customer.domain.dto.CustomerInfo;
import com.moving.shop.customer.domain.dto.CustomerRequestForm;
import com.moving.shop.customer.domain.entity.CashBalanceHistory;
import com.moving.shop.customer.service.CustomerCashService;
import com.moving.shop.customer.service.CustomerRequestService;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerCashService cashService;
  private final CustomerRequestService customerRequestService;

  @PostMapping("/cash")
  @PreAuthorize("hasAuthority('CUSTOMER')")
  @ApiOperation(value="고객 캐쉬 변경 API", notes = "로그인한 고객이 자신의 캐쉬 잔액을 변경합니다.")
  public ResponseEntity<ChangeCashForm> changeCash(
      @RequestHeader(value = TOKEN_HEADER) String token, @Valid @RequestBody ChangeCashForm form) {

    String refinedToken = token.substring(TOKEN_PREFIX.length());
    return ResponseEntity.ok(cashService.changeCashBalance(refinedToken, form));
  }

  @PostMapping("/requests")
  @PreAuthorize("hasAuthority('CUSTOMER')")
  @ApiOperation(value="고객 서비스요청서 작성 API", notes = "로그인한 고객이 서비스 요청서를 작성합니다..")
  public ResponseEntity<CustomerRequestForm> addCustomerRequest(@RequestHeader(value = TOKEN_HEADER) String token,
      @Valid @RequestBody CustomerRequestForm form) {

    String refinedToken = token.substring(TOKEN_PREFIX.length());
    return ResponseEntity.ok(customerRequestService.addCustomerRequest(refinedToken, form));
  }

  @GetMapping
  @PreAuthorize("hasAuthority('CUSTOMER')")
  @ApiOperation(value="고객 내 정보 확인 API", notes = "로그인한 고객이 자신의 정보를 확인합니다.")
  public ResponseEntity<CustomerInfo> getCustomerInfo(@RequestHeader(value = TOKEN_HEADER) String token) {

    String refinedToken = token.substring(TOKEN_PREFIX.length());
    return ResponseEntity.ok(customerRequestService.getCustomerInfo(refinedToken));
  }

}
