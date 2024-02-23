package ie.todolist.api.service.todolist;

import ie.todolist.api.service.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/rest")
public class TodolistController {

  private final TodolistRepository todolistRepository;
  private final UserRepository userRepository;

  @GetMapping("/hello")
  public ResponseEntity<String> getHello(){
    return ResponseEntity.ok("Hello, World!");
  }

  @GetMapping("/todolist")
  public ResponseEntity<TodolistEntity> getTodolist(@RequestBody TodolistRequest request){
    var todolist = todolistRepository.findById(request.id)
      .orElseThrow();
    return ResponseEntity.ok(todolist);
  }

  @GetMapping("/todolists")
  public ResponseEntity<List<TodolistEntity>> getTodolists(TodolistsRequest request){
//    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//    var user = userRepository.findByEmail(auth.getName());
//    var userId = user.getId();
//    var todolists = todolistRepository.findByUserId(userId)
//      .orElseThrow();
    var todolists = todolistRepository.findAll();
    return ResponseEntity.ok(todolists);
  }

  public record TodolistRequest(Integer id){}
  public record TodolistsRequest(Integer userId){}
}
