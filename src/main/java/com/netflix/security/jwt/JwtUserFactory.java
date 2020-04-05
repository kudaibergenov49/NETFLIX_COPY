package com.netflix.security.jwt;

import com.netflix.model.Role;
import com.netflix.model.Status;
import com.netflix.model.User;
import com.netflix.security.jwt.JwtUser;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor
public final class JwtUserFactory {

  public static JwtUser create(User user) {
    return JwtUser.builder()
        .id(user.getId())
        .username(user.getUsername())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .email(user.getEmail())
        .password(user.getPassword())
        .authorities(mapToGrantedAuthorities(user.getRoles()))
        .enabled(user.getStatus().equals(Status.ACTIVE))
        .lastPasswordResetDate(user.getUpdated())
        .build();
  }

  private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> roles) {
    return roles.stream().map(Role::getName).map(SimpleGrantedAuthority::new).collect(toList());
  }
}
