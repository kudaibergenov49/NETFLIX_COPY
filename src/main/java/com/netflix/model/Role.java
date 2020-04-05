package com.netflix.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "roles")
@Data
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @CreatedDate
  @Column(name = "created")
  private Date created;

  @LastModifiedDate
  @Column(name = "updated")
  private Date updated;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  Status status;

  @Column(name = "name")
  private String name;

  @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
  private List<User> users;

  @Override
  public String toString() {
    return "Role{" +
            "id: " + id + ", " +
            "name: " + name + "}";
  }
}
