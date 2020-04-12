package com.netflix.service.impl;

import com.netflix.model.User;
import com.netflix.repository.RoleRepository;
import com.netflix.repository.UserRepository;
import com.netflix.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.netflix.model.Status.ACTIVE;
import static java.util.Arrays.asList;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  public UserServiceImpl(RoleRepository roleRepository, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
    this.roleRepository = roleRepository;
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = passwordEncoder;
  }

  @Override
  public User register(User user) {
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    user.setRoles(asList(roleRepository.findByName("ROLE_USER")));
    user.setStatus(ACTIVE);

    User registerUser = userRepository.save(user);
    log.info("IN register - user: {} successfully registered ", registerUser);
    return registerUser;
  }

  @Override
  public List<User> getAll() {
    List<User> users = userRepository.findAll();
    log.info("IN getAll - {} users found", users.size());
    return users;
  }

  @Override
  public User findByUserName(String username) {
    User user = userRepository.findByUsername(username);
    log.info("IN findByUsername - user: {} found by username: {}", user, username);
    return user;
  }

  @Override
  public User findById(Long id) {
    User user = userRepository.findById(id).orElse(null);
    if (user == null) {
      log.warn("IN findById - no user found by id: {}", id);
    } else {
      log.info("IN findById - user: {} found by id: {}", user, id);
    }
    return user;
  }

  @Override
  public void delete(Long id) {
    userRepository.deleteById(id);
  }
}
