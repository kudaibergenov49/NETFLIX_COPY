package com.netflix.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.netflix.model.Status;
import com.netflix.model.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@NoArgsConstructor
public class AdminUserDto {
  private Long id;
  private String username;
  private String firstName;
  private String lastName;
  private String email;
  private String status;

  public User toUser() {
    return User.builder()
        .id(id)
        .username(username)
        .firstName(firstName)
        .lastName(lastName)
        .email(email)
        .status(Status.valueOf(status))
        .build();
  }

  public static AdminUserDto fromUser(User user) {
    return AdminUserDto.builder()
        .id(user.getId())
        .username(user.getUsername())
        .lastName(user.getLastName())
        .firstName(user.getFirstName())
        .email(user.getEmail())
        .status(user.getStatus().name())
        .build();
  }
}
