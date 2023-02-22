package com.moving.shop.product.domain.dto;

import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddServiceProductForm {

  /* 서비스상품_옵션 */
  private List<AddProductOptionForm> productOptions;

  /* 서비스상품명 */
  @NotBlank(message = "상품명은 필수값입니다.")
  private String name;

  /* 상품개요설명 */
  @NotBlank(message = "상품개요설명은 필수값입니다.")
  private String outlineDescription;

  /* 상품 금액 */
  @Positive(message="상품 금액은 양수여야 합니다.")
  private int productPrice;

  /* 서비스 실행일시 */
  @Future(message = "서비스 실행일시는 현재보다 미래여야 합니다.")
  private LocalDateTime executeDate;

  /* 고객 서비스 요청서 ID */
  @Positive(message="유효한 고객 서비스 요청서가 아닙니다.")
  private Long serviceRequestId;
}
