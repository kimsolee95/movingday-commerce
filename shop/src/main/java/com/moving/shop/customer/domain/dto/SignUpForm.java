package com.moving.shop.customer.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpForm {

  @NotBlank(message = "회원가입 시 이메일은 필수값입니다.")
  @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
  private String email;

  @NotBlank(message = "회원가입 시 이름은 필수값입니다.")
  private String name;

  @NotBlank(message = "회원가입 시 비밀번호는 필수값입니다.")
  private String password;

  @NotBlank(message = "회원가입 시 전화번호는 필수값입니다.")
  private String phone;
}
