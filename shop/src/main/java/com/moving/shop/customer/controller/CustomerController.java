package com.moving.shop.customer.controller;

import com.moving.shop.customer.domain.dto.ChangeCashForm;
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


  @PostMapping("/cash")
  @PreAuthorize("hasAuthority('CUSTOMER')")
  public ResponseEntity<Integer> changeCash(@RequestBody ChangeCashForm form) {

    return ResponseEntity.ok(1111);
  }

}
