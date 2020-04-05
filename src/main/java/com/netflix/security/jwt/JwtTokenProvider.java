package com.netflix.security.jwt;

import com.netflix.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.startsWith;
import static org.apache.commons.lang3.StringUtils.substring;

@Component
public class JwtTokenProvider {

  @Value("${jwt.token.secret}")
  private String secret;

  @Value("${jwt.token.expired}")
  private Long validInMs;

  private final UserDetailsService userDetailsService;

  public JwtTokenProvider(
      @Lazy @Qualifier("jwtUserDetailsService") UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @Bean
  BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @PostConstruct
  protected void init() {
    secret = Base64.getEncoder().encodeToString(secret.getBytes());
  }

  public String createToken(String username, List<Role> roles) {
    Claims claims = Jwts.claims().setSubject(username);
    claims.put("roles", roles.stream().map(Role::getName).collect(toList()));

    Date now = new Date();

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(new Date(now.getTime() + validInMs))
        .signWith(SignatureAlgorithm.HS256, secret)
        .compact();
  }

  public Authentication getAuthentication(String token) {
    UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public String getUsername(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
  }

  public String resolveToken(HttpServletRequest request) {
    return Optional.ofNullable(request.getHeader("Authorization"))
        .filter(token -> startsWith(token, "Bearer_"))
        .map(token -> substring(token, 7, token.length()))
        .orElse(null);
  }

  public boolean validateToken(String token) {
    try {
      return !(Jwts.parser()
          .setSigningKey(secret)
          .parseClaimsJws(token)
          .getBody()
          .getExpiration()
          .before(new Date()));
    } catch (JwtException | IllegalArgumentException e) {
      throw new JwtAuthenticationException("jwt token is expired or invalid");
    }
  }
}
