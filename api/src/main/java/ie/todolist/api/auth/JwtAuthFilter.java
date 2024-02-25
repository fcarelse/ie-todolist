package ie.todolist.api.auth;

import ie.todolist.api.service.session.Session;
import ie.todolist.api.service.session.SessionRepository;
import ie.todolist.api.service.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;
  private final SessionRepository sessionRepository;
  private final UserRepository userRepository;

  @Override
  protected void doFilterInternal(
    @NonNull HttpServletRequest request,
    @NonNull HttpServletResponse response,
    @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    final String authHeader = request.getHeader("Authorization");
    final String jwt;
    final String sessionID;
    final Session session;
    final String userEmail;
    if(authHeader == null || !authHeader.startsWith("Bearer ")){
      filterChain.doFilter(request,response);
      return;
    }
    jwt = authHeader.substring(7);
    sessionID = jwtService.extractSubject(jwt);
    session = sessionRepository.findById(sessionID)
      .orElse(null);
    if(session == null){
      response.sendError(403, "Token invalid");
      return;
    }
    userEmail = session.getEmail();
    if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
      if(jwtService.isTokenValid(jwt)){
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
          userDetails,
          null,
          userDetails.getAuthorities()
        );
        authToken.setDetails(
          new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }
    filterChain.doFilter(request,response);
  }
}
