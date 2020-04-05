package com.netflix.security;

import com.netflix.model.User;
import com.netflix.security.jwt.JwtUser;
import com.netflix.security.jwt.JwtUserFactory;
import com.netflix.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Qualifier("jwtUserDetailsService")
public class JwtUserDetailsService implements UserDetailsService {

  private final UserService userService;

  @Autowired
  public JwtUserDetailsService(UserService userService) {
    this.userService = userService;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userService.findByUserName(username);
    if (user == null) {
      throw new UsernameNotFoundException("User with username: " + username + " not fount");
    }

    JwtUser jwtUser = JwtUserFactory.create(user);
    log.info("IN loadByUsername - user with username : {} successfully loaded", username);

    return jwtUser;
  }
}
