package ie.todolist.api.app;

import ie.todolist.api.service.user.UserEntity;
import ie.todolist.api.service.user.UserRepository;
import ie.todolist.api.service.role.Role;
import ie.todolist.api.service.todo.TodoEntity;
import ie.todolist.api.service.todo.TodoRepository;
import ie.todolist.api.service.todolist.TodolistEntity;
import ie.todolist.api.service.todolist.TodolistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DemoData {
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final TodoRepository todoRepository;
  private final TodolistRepository todolistRepository;

  @EventListener(ApplicationReadyEvent.class)
  public void initDemoData() {
    var user = UserEntity.builder()
      .first("Bob")
      .last("Smith")
      .email("bob@todolist.ie")
      .password(passwordEncoder.encode("password"))
      .locked(false)
      .verified(true)
      .role(Role.USER)
      .build();
    userRepository.save(user);
    user = userRepository.findByEmail("bob@todolist.ie")
      .orElseThrow();
    todoRepository.save(new TodoEntity(null,user.getId(),0,"done","First Todo","Finish Openapi.yml"));
    todoRepository.save(new TodoEntity(null,user.getId(),1,"todo","Second Todo","Frontend"));
    var todos = todoRepository.findByUserId(user.getId())
      .orElseThrow();
    var todoIds = todos.stream().map(TodoEntity::getId).toList();
    var todolist = TodolistEntity.builder()
      .userId(user.getId())
      .todoIds(todoIds)
      .build();
    todolistRepository.save(todolist);
    System.out.println("Initialized Demo Data");
  }

}
