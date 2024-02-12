package ie.todolist.api.auth.login;

import ie.todolist.api.service.MessageResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class LoginController {

  private final LoginService service;

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> authenticate(
    @NonNull @RequestBody LoginRequest request
  ){
    return ResponseEntity.ok(service.login(request));
  }

  @PostMapping("/logout")
  public ResponseEntity<MessageResponse> logout(
    HttpServletRequest request
  ){
    return ResponseEntity.ok(service.logout(request));
  }
}
