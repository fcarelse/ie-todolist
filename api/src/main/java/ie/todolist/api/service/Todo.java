package ie.todolist.api.service;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "todolist")
public class Todo {
  @Id
  @GeneratedValue
  private Integer id;
  private String summary;
  private String details;
}
