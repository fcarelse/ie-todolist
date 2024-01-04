package ie.todolist.api.service;

import ie.todolist.api.auth.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
  public ResponseEntity<Todolist> getTodolist(@RequestBody TodolistRequest request){
    var todolist = todolistRepository.findById(request.id)
      .orElseThrow();
    return ResponseEntity.ok(todolist);
  }

  @GetMapping("/todolists")
  public ResponseEntity<List<Todolist>> getTodolists(@RequestBody TodolistsRequest request){
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
