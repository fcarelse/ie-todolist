package ie.todolist.api.auth;

import ie.todolist.api.service.session.Session;
import ie.todolist.api.service.session.SessionRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

  private final SessionRepository sessionRepository;
  private static final String SECRET_KEY = "8d8a367e5e6db287e081c61d653174e8cd43d4ce4a374a9a9aa9c227d4d91034";

  public String extractSubject(String token){
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public String generateToken(Session session){
    return generateToken(new HashMap<>(), session);
  }

  public String generateToken(
    Map<String, Object> extraClaims,
    Session session
  ){
    return Jwts.builder()
      .setClaims(extraClaims)
      .setSubject(session.getId())
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(session.getExpiry())
      .signWith(getSigningKey(), SignatureAlgorithm.HS256)
      .compact();
  }

  public Boolean isTokenValid(String token){
    sessionRepository.findById(extractSubject((token)))
      .orElseThrow();
    return !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token){
    return Jwts.parserBuilder()
      .setSigningKey(getSigningKey())
      .build()
      .parseClaimsJws(token)
      .getBody();
  }

  private Key getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
