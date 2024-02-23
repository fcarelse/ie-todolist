package ie.todolist.api.service.todo;

import ie.todolist.api.service.session.SessionRepository;
import ie.todolist.api.service.user.UserEntity;
import ie.todolist.api.service.user.UserRepository;
import ie.todolist.api.service.response.MessageResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/rest")
public class TodoController {
  public final TodoRepository todoRepository;
  public final UserRepository userRepository;
  public final SessionRepository sessionRepository;

  @GetMapping("/todos")
  public ResponseEntity<List<TodoEntity>> getTodos(){
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if(auth==null) return ResponseEntity.ok(List.of());
    UserEntity user = (UserEntity) auth.getPrincipal();
    System.out.println(user.toString());
    var todos = todoRepository.findByUserId(user.getId())
      .orElseThrow();
    todos.sort(Comparator.comparing(TodoEntity::getIndex));
    int i=0;
    for(TodoEntity todo : todos){
      todo.setIndex(i);
      todoRepository.save(todo);
      i++;
    }
    return ResponseEntity.ok(todos);
  }

  @PostMapping("/todos")
  public ResponseEntity<TodoEntity> postTodo(){
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserEntity user = (UserEntity) auth.getPrincipal();
    List<TodoEntity> todos = todoRepository.findByUserId(user.getId())
      .orElseThrow();
    TodoEntity todo = new TodoEntity(null,user.getId(),todos.toArray().length,"todo","New Todo","New Details");
    todoRepository.save(todo);
    return ResponseEntity.ok(todo);
  }

  @GetMapping("/todos/{id}")
  public ResponseEntity<TodoEntity> getTodo(@PathVariable String id){
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserEntity user = (UserEntity) auth.getPrincipal();
    var todo = todoRepository.findById(id)
      .orElseThrow();
    if(!todo.getUserId().equals(user.getId())) ResponseEntity.ok(todo);
    return ResponseEntity.ok(todo);
  }

  @PutMapping("/todos/{id}")
  public ResponseEntity<MessageResponse> putTodo(@PathVariable String id,@RequestBody TodoEntity newTodo){
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserEntity user = (UserEntity) auth.getPrincipal();
    TodoEntity todo = todoRepository.findById(id).orElseThrow();
    todo.setDetails(newTodo.getDetails());
    todo.setSummary(newTodo.getSummary());
    todo.setStatus(newTodo.getStatus());
    todoRepository.save(todo);
    return ResponseEntity.ok(new MessageResponse("Successfully Updated Todo",202));
  }

  @DeleteMapping("/todos/{id}")
  public ResponseEntity<MessageResponse> deleteTodo(@PathVariable String id){
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserEntity user = (UserEntity) auth.getPrincipal();
    System.out.println(user.toString());
    var todo = todoRepository.findById(id)
      .orElseThrow();
    todoRepository.delete(todo);
    return ResponseEntity.ok(new MessageResponse("Successfully Deleted Todo",204));
  }


}
