package ie.todolist.api.auth.login;

import ie.todolist.api.auth.*;
import ie.todolist.api.service.response.MessageResponse;
import ie.todolist.api.service.session.Session;
import ie.todolist.api.service.session.SessionRepository;
import ie.todolist.api.service.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class LoginService {
  private final UserRepository userRepository;
  private final SessionRepository sessionRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public LoginResponse login(LoginRequest request) {
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
    return LoginResponse.builder()
      .token(jwtToken)
      .build();
  }

  public MessageResponse logout(LogoutRequest request) {
    final String jwt = request.getToken();
    final String sessionID;
    sessionID = jwtService.extractSubject(jwt);
    var session = sessionRepository.findById(sessionID)
      .orElse(null);
    if(session == null){
      return new MessageResponse(403, "No session found");
    }
    System.out.println(session.getEmail());
    sessionRepository.delete(session);
    return new MessageResponse("Logged out", 200);
  }

  public Boolean loggedIn(HttpServletRequest request) {
    final String authHeader = request.getHeader("Authorization");
    final String jwt;
    final String sessionID;
    if(authHeader == null || !authHeader.startsWith("Bearer ")){
      return false;
    }
    jwt = authHeader.substring(7);
    sessionID = jwtService.extractSubject(jwt);
    var session = sessionRepository.findById(sessionID)
      .orElse(null);
    return session != null;
  }


}
