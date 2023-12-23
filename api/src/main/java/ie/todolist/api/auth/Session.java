package ie.todolist.api.auth;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;

@Entity
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "sessions")
public class Session {
  @Id()
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  @Column(unique = true)
  private String email;
  private Date expiry;
}
