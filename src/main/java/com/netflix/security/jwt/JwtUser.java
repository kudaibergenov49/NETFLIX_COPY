package com.netflix.security.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

@Builder
@AllArgsConstructor
public class JwtUser implements UserDetails {
  private final Long id;
  private final String username;
  private final String firstName;
  private final String lastName;
  private final String password;
  private final String email;
  private final boolean enabled;
  private final Date lastPasswordResetDate;
  private final Collection<? extends GrantedAuthority> authorities;

  @JsonIgnore
  public Long getId() {
    return id;
  }

  @Override
  public String getUsername() {
    return username;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  @JsonIgnore
  @Override
  public String getPassword() {
    return password;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public boolean isEnabled() {
    return this.enabled;
  }

  @JsonIgnore
  public Date getLastPasswordResetDate() {
    return lastPasswordResetDate;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }
}
