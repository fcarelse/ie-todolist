package ie.todolist.api.service.todo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<TodoEntity, Integer> {
  Optional<TodoEntity> findById(String id);
  Optional<List<TodoEntity>> findByUserId(String id);
}
