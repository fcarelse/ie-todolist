package ie.todolist.api.auth.login;

import ie.todolist.api.service.response.MessageResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class LoginController {

  private final LoginService service;

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(
    @NonNull @RequestBody LoginRequest request
  ){
    return ResponseEntity.ok(service.login(request));
  }

  @PostMapping("/logout")
  public ResponseEntity<MessageResponse> logout(
    @RequestBody LogoutRequest request
  ){
    return ResponseEntity.ok(service.logout(request));
  }

  @GetMapping("/loggedIn")
  public ResponseEntity<Boolean> loggedIn(
    @RequestBody HttpServletRequest request
  ){
    return ResponseEntity.ok(service.loggedIn(request));
  }

}
