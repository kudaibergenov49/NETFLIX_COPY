package com.netflix.repository;

import com.netflix.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieDao extends JpaRepository<Movie, Long> {

  Movie findByName(String name);

  Optional<Movie> findById(Long id);
}
