package ie.todolist.api.Controller;

import ie.todolist.api.Model.MessageResponse;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class TodolistController {

  @GetMapping(value="/rest/todolist", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<MessageResponse> restTodolist(){
    List<Todolist> todolists = new List<Todolist>();
    return ResponseEntity.ok(todolists);
  }

}
