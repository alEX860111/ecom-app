package net.brainified.domain.authentication;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.time.LocalDate;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import reactor.core.publisher.Mono;

@Service
final class JWTAuthenticationTokenService implements AuthenticationTokenService {

  private final ReactiveAuthenticationManager authenticationManager;

  public JWTAuthenticationTokenService(final ReactiveAuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  public Mono<AuthenticationToken> createToken(final LoginData loginData) {
    final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        loginData.getUsername(),
        loginData.getPassword());

    return authenticationManager.authenticate(authenticationToken)
        .doOnSuccess(auth -> SecurityContextHolder.getContext().setAuthentication(auth))
        .map(this::createAuthenticationResult);
  }

  private AuthenticationToken createAuthenticationResult(final Authentication authentication) {
    try {
      final Algorithm algorithm = Algorithm.HMAC256("secret");

      final String token = JWT.create()
          .withIssuer("net.brainified")
          .withIssuedAt(Date.valueOf(LocalDate.now()))
          .withSubject(authentication.getName())
          .withArrayClaim("role", getRoles(authentication))
          .sign(algorithm);

      return new AuthenticationToken(token);
    } catch (final UnsupportedEncodingException exception) {
      // UTF-8 encoding not supported
    } catch (final JWTCreationException exception) {
      // Invalid Signing configuration / Couldn't convert Claims.
    }
    throw new RuntimeException();

  }

  private String[] getRoles(final Authentication authentication) {
    return authentication.getAuthorities()
        .stream()
        .map(GrantedAuthority::getAuthority)
        .toArray(String[]::new);
  };

}
