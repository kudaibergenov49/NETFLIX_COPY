package com.netflix.service.impl;

import com.netflix.repository.RoleDao;
import com.netflix.repository.UserDao;
import com.netflix.model.User;
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

  private final UserDao userDao;
  private final RoleDao roleDao;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  public UserServiceImpl(RoleDao roleDao, UserDao userDao, BCryptPasswordEncoder passwordEncoder) {
    this.roleDao = roleDao;
    this.userDao = userDao;
    this.bCryptPasswordEncoder = passwordEncoder;
  }

  @Override
  public User register(User user) {
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    user.setRoles(asList(roleDao.findByName("ROLE_USER")));
    user.setStatus(ACTIVE);

    User registerUser = userDao.save(user);
    log.info("IN register - user: {} successfully registered ", registerUser);
    return registerUser;
  }

  @Override
  public List<User> getAll() {
    List<User> users = userDao.findAll();
    log.info("IN getAll - {} users found", users.size());
    return users;
  }

  @Override
  public User findByUserName(String username) {
    User user = userDao.findByUsername(username);
    log.info("IN findByUsername - user: {} found by username: {}", user, username);
    return user;
  }

  @Override
  public User findById(Long id) {
    User user = userDao.findById(id).orElse(null);
    if (user == null) {
      log.warn("IN findById - no user found by id: {}", id);
    } else {
      log.info("IN findById - user: {} found by id: {}", user, id);
    }
    return user;
  }

  @Override
  public void delete(Long id) {
    userDao.deleteById(id);
  }
}
