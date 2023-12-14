package ie.todolist.api;

import ie.todolist.api.auth.User;
import ie.todolist.api.auth.UserRepository;
import ie.todolist.api.service.Todo;
import ie.todolist.api.service.Todolist;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
@Configuration
@RequiredArgsConstructor
public class TodolistAPI {

	@Value("${server.port:3000}")
	private int port;

	private final UserRepository userRepository;
//	private final PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(TodolistAPI.class, args);
	}

//	@EventListener(ApplicationReadyEvent.class)
//	public void initData() {
//		var user = User.builder()
//			.first("Bob")
//			.last("Smith")
//			.email("bob@todolist.ie")
//			.password(passwordEncoder.encode("password"))
//			.build();
//		userRepository.save(user);
//		user = userRepository.findByEmail("bob@todolist.ie")
//			.orElseThrow();
//		var todo1 = new Todo(null,user.getId(),"First Todo","Finish Openapi.yml");
//		var todo2 = new Todo(null,user.getId(),"Second Todo","Frontend");
//		var todolist = Todolist.builder()
//			.userId(user.getId())
//			.status(Todolist.TodoStatus.TODO)
//			.todoIds(List.of(todo1.getId(),todo2.getId()))
//			.build();
//	}

	@Bean
	public AuthenticationProvider authenticationProvider(){
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
		return config.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService(){
		return username -> userRepository.findByEmail(username)
			.orElseThrow(()->new UsernameNotFoundException("User not found"));
	}
}
