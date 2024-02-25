package ie.todolist.api.auth;

import ie.todolist.api.auth.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class Security {

  private final JwtAuthFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;
  private static final String[] WHITELIST = {
    "/api/user/**",
    "/api/rest/hello",
  };

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
    http
      .csrf(AbstractHttpConfigurer::disable)
      .authorizeHttpRequests(authorize->authorize
        .requestMatchers(WHITELIST).permitAll()
        .anyRequest()
          .permitAll()
          //.authenticated()
      )
      .sessionManagement(session->session.sessionCreationPolicy(STATELESS))
//      .authenticationProvider(authenticationProvider)
      .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
//      .logout(logout->logout.logoutUrl("/api/user/logout"))
//      .formLogin(formLogin->formLogin
//        .loginPage("/auth/authenticate")
//        .permitAll()
//      )
    ;
    return http.build();
  }
}
