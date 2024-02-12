package ie.todolist.api.service;

import ie.todolist.api.auth.SessionRepository;
import ie.todolist.api.auth.User;
import ie.todolist.api.auth.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/rest")
public class TodoController {
  public final TodoRepository todoRepository;
  public final UserRepository userRepository;
  public final SessionRepository sessionRepository;

  @GetMapping("/todos")
  public ResponseEntity<List<Todo>> getTodos(){
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User user = (User) auth.getPrincipal();
//    System.out.println(user.getId());
    var todos = todoRepository.findByUserId(user.getId())
      .orElseThrow();
    return ResponseEntity.ok(todos);
  }

  @PostMapping("/todo")
  public ResponseEntity<MessageResponse> postTodo(@RequestBody Todo todo){
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User user = (User) auth.getPrincipal();
    todo.setUserId(user.getId());
    todoRepository.save(todo);
    return ResponseEntity.ok(new MessageResponse("Successfully created todo", 201));
  }

}
