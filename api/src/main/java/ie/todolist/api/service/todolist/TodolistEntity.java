package ie.todolist.api.service.todolist;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "todolist")
public class TodolistEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  private String userId;
  private String summary;
  private String details;
  private List<String> todoIds;

}
