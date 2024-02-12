package ie.todolist.api.service;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@ToString
@EqualsAndHashCode(callSuper = true)
public class DataResponse extends MessageResponse {
  @Getter @Setter
  private Map<String, Object> data;

  public DataResponse(String message, Integer success, Map<String, Object> data) {
    super(message, success);
    this.data = data;
  }
}
