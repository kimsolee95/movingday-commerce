package com.moving.shop.common.security;

import com.moving.shop.customer.service.CustomerSignUpService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class TokenProvider {

  private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60; //1 hour
  private static final String KEY_ROLES = "roles";

  private final CustomerSignUpService customerSignUpService;

  @Value("${spring.jwt.secret}")
  private String secretKey;

  public String generateToken(String username, String roles) {

    Claims claims = Jwts.claims().setSubject(username);
    claims.put(KEY_ROLES, roles);

    var now = new Date();
    var expiredDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME);

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now) //토큰 발행 시점
        .setExpiration(expiredDate) //토큰 만료 시점
        .signWith(SignatureAlgorithm.HS512, this.secretKey)
        .compact();
  }

  public Authentication getAuthentication(String jwt) {

    UserDetails userDetails = customerSignUpService.loadUserByUsername(this.getUsername(jwt));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public boolean validateToken(String token) {

    if (!StringUtils.hasText(token)) {
      return false;
    }

    var claims = this.parseClaims(token);
    return !claims.getExpiration().before(new Date());
  }

  public String getUsername(String token) {
    return this.parseClaims(token).getSubject();
  }

  private Claims parseClaims(String token) {

    try {

      return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
    }catch (ExpiredJwtException e) {
      return e.getClaims();
    }
  }

}
