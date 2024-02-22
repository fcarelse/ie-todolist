package ie.todolist.api.auth.register;

import ie.todolist.api.auth.*;
import ie.todolist.api.service.MessageResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class RegisterService {
  private final UserRepository userRepository;
  private final SessionRepository sessionRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public RegisterResponse register(RegisterRequest request) {
    var user = User.builder()
      .first(request.getFirst())
      .last(request.getLast())
      .email(request.getEmail())
      .password(passwordEncoder.encode(request.getPassword()))
      .role(UserRole.USER)
      .locked(false)
      .verified(true) // To be set to false once verification process enabled
      .build();
    userRepository.save(user);
    var session = Session.builder()
      .email(request.getEmail())
      .expiry(new Date(System.currentTimeMillis()+(1000 * 60 * 60 * 24)))
      .build();
    sessionRepository.save(session);
    var jwtToken = jwtService.generateToken(session);
    return RegisterResponse.builder()
      .token(jwtToken)
      .build();
  }

  public RegisterResponse login(RegisterRequest request) {
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        request.getEmail(),
        request.getPassword()
      )
    );
    var user = userRepository.findByEmail(request.getEmail())
      .orElseThrow();
    var session = Session.builder()
      .email(user.getEmail())
      .expiry(new Date(System.currentTimeMillis()+(1000 * 60 * 60 * 24)))
      .build();
    sessionRepository.save(session);
    var jwtToken = jwtService.generateToken(session);
    return RegisterResponse.builder()
      .token(jwtToken)
      .build();
  }

  public MessageResponse logout(HttpServletRequest request) {
    final String authHeader = request.getHeader("Authorization");
    final String jwt;
    final String sessionID;
    if(authHeader == null || !authHeader.startsWith("Bearer ")){
      return new MessageResponse(403, "No auth token");
    }
    jwt = authHeader.substring(7);
    sessionID = jwtService.extractSubject(jwt);
    var session = sessionRepository.findById(sessionID)
      .orElseThrow();
    System.out.println(session.getEmail());
    sessionRepository.delete(session);
    return new MessageResponse("Logged out", 200);
  }

}
