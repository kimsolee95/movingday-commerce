package com.moving.shop.customer.application;

import com.moving.shop.common.exception.customexception.CustomerException;
import com.moving.shop.common.exception.type.CustomerErrorCode;
import com.moving.shop.customer.domain.dto.SignUpForm;
import com.moving.shop.customer.domain.entity.Customer;
import com.moving.shop.customer.service.CustomerSignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerSignUpApplication {

  private final CustomerSignUpService customerSignUpService;

  public String customerSignUp(SignUpForm form) {

    if (customerSignUpService.isMailExist(form.getEmail())) {
      throw new CustomerException(CustomerErrorCode.EMAIL_ALREADY_EXIST);
    }

    Customer customer = customerSignUpService.signUp(form);
    //인증용 메시지 전송 로직 추가 필요
    return "회원가입에 성공했습니다.";
  }

}
