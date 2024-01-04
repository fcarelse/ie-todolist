package ie.todolist.api.auth;

import ie.todolist.api.auth.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Integer> {
//  Optional<Session> findByEmail(String email);
  Optional<Session> findById(String id);
  Optional<Long> deleteById(String id);
}
