package ie.todolist.api.auth;

import ie.todolist.api.service.MessageResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.lang.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
    @RequestBody RegisterRequest request
  ){
    return ResponseEntity.ok(service.register(request));
  }

  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> authenticate(
    @RequestBody AuthenticationRequest request
  ){
    return ResponseEntity.ok(service.login(request));
  }

  @PostMapping("/logout")
  public ResponseEntity<MessageResponse> logout(
    @NonNull @RequestBody HttpServletRequest request
  ){
    return ResponseEntity.ok(service.logout(request));
  }
}
