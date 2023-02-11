package com.moving.shop.common.exception;

import com.moving.shop.common.exception.customexception.CustomerException;
import com.moving.shop.common.exception.model.ErrorResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  /*
  * customer exception 핸들러
  * */
  @ExceptionHandler({CustomerException.class})
  public ResponseEntity<ErrorResponse> customerExceptionHandler(HttpServletRequest request,
      CustomerException e) {

    return ResponseEntity.status(e.getStatus())
        .body(ErrorResponse.builder()
            .code(e.getCustomerErrorCode().name())
            .message(e.getCustomerErrorCode().getDescription())
            .status(e.getStatus())
            .build());
  }





}
