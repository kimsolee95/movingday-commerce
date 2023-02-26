package com.moving.shop.common.exception;

import com.moving.shop.common.exception.customexception.CompanyException;
import com.moving.shop.common.exception.customexception.CustomerException;
import com.moving.shop.common.exception.customexception.OrderException;
import com.moving.shop.common.exception.model.ErrorResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
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

  /**
   * company exception 핸들러
   * */
  @ExceptionHandler({CompanyException.class})
  public ResponseEntity<ErrorResponse> companyExceptionHandler(HttpServletRequest request,
      CompanyException e) {

    return ResponseEntity.status(e.getStatus())
        .body(ErrorResponse.builder()
            .code(e.getCompanyErrorCode().name())
            .message(e.getCompanyErrorCode().getDescription())
            .status(e.getStatus())
            .build());
  }

  /**
   * order exception 핸들러
   * */
  @ExceptionHandler({OrderException.class})
  public ResponseEntity<ErrorResponse> orderExceptionHandler(HttpServletRequest request,
      OrderException e) {

    return ResponseEntity.status(e.getStatus())
        .body(ErrorResponse.builder()
            .code(e.getOrderErrorCode().name())
            .message(e.getOrderErrorCode().getDescription())
            .status(e.getStatus())
            .build());
  }

  /**
   * @Valid 유효성 검사 실패 시, exception 핸들러
   * */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> methodValidExceptionHandler(HttpServletRequest request,
      MethodArgumentNotValidException e) {

    BindingResult bindingResult = e.getBindingResult();
    String errorMessage =
        bindingResult.hasErrors() ? bindingResult.getFieldError().getDefaultMessage() : "";

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ErrorResponse.builder()
            .status(HttpStatus.BAD_REQUEST.value())
            .message(errorMessage)
            .code("NOT_CORRECT_FORMAT")
            .build());
  }

}
