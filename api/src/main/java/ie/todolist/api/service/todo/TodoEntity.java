package ie.todolist.api.service.todo;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.Comparator;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "todos")
public class TodoEntity implements Comparable{
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  private String userId;
  private Integer index;
  @Value("Todo")
  private String status;
  private String summary;
  private String details;

  public int compareTo(TodoEntity todo) {
    return Integer.compare(this.index, todo.index);
  }

  @Override
  public int compareTo(Object o) {
    return 0;
  }

  public enum TodoStatus{
    TODO("Todo"),
    DONE("Done");
    public final String label;
    private TodoStatus(String label){
      this.label = label;
    }
  }
}
