package ie.todolist.api.service.role;

public enum Role {
  USER("user"),
  ADMIN("admin");
  public final String label;
  private Role(String label){
    this.label = label;
  }
}
