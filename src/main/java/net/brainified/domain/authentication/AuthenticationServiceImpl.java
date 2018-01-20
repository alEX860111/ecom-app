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
final class AuthenticationServiceImpl implements AuthenticationService {

  private final ReactiveAuthenticationManager authenticationManager;

  public AuthenticationServiceImpl(final ReactiveAuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  public Mono<AuthenticationResult> authenticate(final AuthenticationData authenticationData) {
    final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        authenticationData.getUsername(), authenticationData.getPassword());

    return authenticationManager.authenticate(authenticationToken)
        .doOnSuccess(auth -> SecurityContextHolder.getContext().setAuthentication(auth))
        .map(this::createAuthenticationResult);
  }

  private AuthenticationResult createAuthenticationResult(final Authentication authentication) {
    try {
      final Algorithm algorithm = Algorithm.HMAC256("secret");

      final String token = JWT.create()
          .withIssuer("net.brainified")
          .withIssuedAt(Date.valueOf(LocalDate.now()))
          .withSubject(authentication.getName())
          .withArrayClaim("role", getRoles(authentication))
          .sign(algorithm);

      return new AuthenticationResult(token);
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
