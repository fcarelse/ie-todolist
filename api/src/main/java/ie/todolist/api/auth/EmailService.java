package ie.todolist.api.auth;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Data
public class EmailService {
  @Autowired
  private JavaMailSender mailSender;
  private String to;
  private String from = System.getenv("EMAIL_FROM");
  private String subject;
  private String body;
  private SimpleMailMessage email;

  public void sendEmail(String toEmail, String subject, String body){
    email = new SimpleMailMessage();
    send();
  }

  public void send(){
    email.setFrom(from);
    email.setTo(to);
    email.setSubject(subject);
    email.setText(body);
    mailSender.send(email);
  }
}
