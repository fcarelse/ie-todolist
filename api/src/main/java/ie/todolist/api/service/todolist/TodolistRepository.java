package ie.todolist.api.service.todolist;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodolistRepository extends JpaRepository<TodolistEntity, Integer> {
  Optional<TodolistEntity> findById(String id);
  Optional<List<TodolistEntity>> findByUserId(String userId);
}
