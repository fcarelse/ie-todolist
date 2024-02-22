package ie.todolist.api.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodolistRepository extends JpaRepository<Todolist, Integer> {
  Optional<Todolist> findById(Integer id);
//  Optional<List<Todolist>> findByUserId();
}
