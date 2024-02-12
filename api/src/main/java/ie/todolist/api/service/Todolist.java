package ie.todolist.api.service;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "todolist")
public class Todolist {
  @Id
  @GeneratedValue
  private Integer id;
  private Integer userId;
  private String summary;
  private String details;
  private TodoStatus status;
  private List<Integer> todoIds;

  public enum TodoStatus{
    TODO("Todo"),
    DONE("Done");
    public final String label;
    private TodoStatus(String label){
      this.label = label;
    }
  }
}
