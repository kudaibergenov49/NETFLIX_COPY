package com.netflix.service;

import com.netflix.model.User;

import java.util.List;

public interface UserService {

  User register(User user);

  List<User> getAll();

  User findByUserName(String username);

  User findById(Long id);

  void delete(Long id);
}
