package com.netflix.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "movie")
@Data
public class Movie {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "creator")
  private String creator;

  /* List<String> cast;

  List<String> genres;*/
  // TODO AFTER

  @Column(name = "maturityRating")
  private Integer maturityRating;

  @Column(name = "description")
  private String description;

  @Column(name = "rate")
  private Integer rate;

  @Column(name = "audience_points", columnDefinition = "bigint default 0")
  private BigInteger audiencePoints;

  @CreatedDate
  @Column(name = "created")
  private Date created;
}
