package com.netflix.security.jwt;

import lombok.SneakyThrows;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class JwtTokenFilter extends GenericFilterBean {

  private JwtTokenProvider jwtTokenProvider;

  public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  @SneakyThrows
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
    Optional.ofNullable(jwtTokenProvider.resolveToken((HttpServletRequest) request))
        .filter(token -> jwtTokenProvider.validateToken(token))
        .ifPresent(
            token -> {
              Optional.ofNullable(jwtTokenProvider.getAuthentication(token))
                  .ifPresent(auth -> SecurityContextHolder.getContext().setAuthentication(auth));
              chain.doFilter(request, response);
            });
  }
}
