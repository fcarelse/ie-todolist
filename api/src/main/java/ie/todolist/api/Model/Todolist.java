package ie.todolist.api.Model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Todolist {
  @Getter @Setter
  private String summary;
  @Getter @Setter
  private String details;
  @Getter @Setter
  private TodoStatus status;

  public enum TodoStatus{
    TODO("Todo"),
    DONE("Done");
    public final String label;
    private TodoStatus(String label){
      this.label = label;
    }
  }
}
