package ie.todolist.api.Controller;

import ie.todolist.api.Model.Todolist;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodolistController {

  @GetMapping(value="/rest/todolist", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<List<Todolist>> getTodolist(){
    List<Todolist> todolists = List.of(new Todolist());
    return ResponseEntity.ok(todolists);
  }

}
