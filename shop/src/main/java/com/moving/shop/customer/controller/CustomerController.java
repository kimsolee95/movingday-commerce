package com.moving.shop.customer.controller;

import static com.moving.shop.common.security.JwtAuthenticationFilter.TOKEN_HEADER;
import static com.moving.shop.common.security.JwtAuthenticationFilter.TOKEN_PREFIX;

import com.moving.shop.customer.domain.dto.ChangeCashForm;
import com.moving.shop.customer.domain.dto.CustomerRequestForm;
import com.moving.shop.customer.domain.entity.CashBalanceHistory;
import com.moving.shop.customer.service.CustomerCashService;
import com.moving.shop.customer.service.CustomerRequestService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
  public ResponseEntity<ChangeCashForm> changeCash(@RequestHeader(value = TOKEN_HEADER) String token, @Valid @RequestBody ChangeCashForm form) {

    String refinedToken = token.substring(TOKEN_PREFIX.length());
    return ResponseEntity.ok(cashService.changeCashBalance(refinedToken, form));
  }

  @PostMapping("/requests")
  @PreAuthorize("hasAuthority('CUSTOMER')")
  public ResponseEntity<CustomerRequestForm> addCustomerRequest(@RequestHeader(value = TOKEN_HEADER) String token,
      @Valid @RequestBody CustomerRequestForm form) {

    String refinedToken = token.substring(TOKEN_PREFIX.length());
    return ResponseEntity.ok(customerRequestService.addCustomerRequest(refinedToken, form));
  }

}
