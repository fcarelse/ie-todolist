package ie.todolist.api.service.response;

import lombok.*;

@ToString
@EqualsAndHashCode
public class MessageResponse {
  @Getter @Setter
  private Integer success;
  @Getter @Setter
  private Integer error;
  @Getter @Setter
  private String message;

  public MessageResponse(String message, Integer success){
    this.message = message;
    this.success = success;
  }

  public MessageResponse(Integer error, String message){
    this.error = error;
    this.message = message;
  }
}
