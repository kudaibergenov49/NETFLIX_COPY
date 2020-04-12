package com.netflix.rest;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

public abstract class AbstractRestController<T, R extends JpaRepository<T, ?>> {
  protected R repository;

  public AbstractRestController(R repository) {
    this.repository = repository;
  }

  @GetMapping
  public Page<T> showAll(@PageableDefault Pageable pageable) {
    return repository.findAll(pageable);
  }

  @GetMapping("{id}")
  public T show(@PathVariable("id") T element) {
    return element;
  }

  @PostMapping
  public T add(@RequestBody T element) {
    return repository.save(element);
  }

  @PutMapping("{id}")
  public T update(@PathVariable("id") T dbElement, @RequestBody T element) {
    BeanUtils.copyProperties(element, dbElement, "id");
    return repository.save(dbElement);
  }

  @DeleteMapping("{id}")
  public void delete(@PathVariable("id") T element) {
    repository.delete(element);
  }
}
