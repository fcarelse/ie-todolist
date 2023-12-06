package ie.todolist.api.Controller;

import ie.todolist.api.Model.MessageResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

  @GetMapping(value="/user/login", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<MessageResponse> login(){
    return ResponseEntity.ok(new MessageResponse("Login Success",200));
  }

  @GetMapping(value="/user/logout", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<MessageResponse> logout(){
    return ResponseEntity.ok(new MessageResponse("Logout Success",200));
  }
}
