package ie.todolist.api.auth.register;

import ie.todolist.api.auth.register.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class RegisterController {

  private final RegisterService service;

  @PostMapping("/register")
  public ResponseEntity<RegisterResponse> register(
    @NonNull @RequestBody RegisterRequest request
  ){
    return ResponseEntity.ok(service.register(request));
  }


}
