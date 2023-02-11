package com.moving.shop.customer.domain.entity;

import com.moving.shop.common.BaseEntity;
import com.moving.shop.customer.domain.dto.SignUpForm;
import com.moving.shop.customer.domain.type.MemberType;
import java.util.Locale;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditOverride;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AuditOverride(forClass = BaseEntity.class)
public class Customer extends BaseEntity {

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String email;

  private String password;

  private String name;

  private String phone;

  @Column(columnDefinition = "int default 0")
  private Integer balanceCash;

  @Enumerated(EnumType.STRING)
  private MemberType memberType;

  public static Customer of(SignUpForm form, String encodedPassword) {
    return Customer.builder()
        .email(form.getEmail().toLowerCase(Locale.ROOT))
        .password(encodedPassword)
        .name(form.getName())
        .phone(form.getPhone())
        .balanceCash(0)
        .memberType(MemberType.CUSTOMER)
        .build();
  }
}
