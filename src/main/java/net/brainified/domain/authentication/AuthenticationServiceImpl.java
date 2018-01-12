package net.brainified.domain.authentication;

import java.io.UnsupportedEncodingException;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
  public Mono<AuthenticationResult> authenticate(final AuthenticationRequest credentials) {
    final Mono<Authentication> authenticate = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword()));

    authenticate.subscribe(auth -> SecurityContextHolder.getContext().setAuthentication(auth));
    return authenticate.map(this::createAuthenticationResult);

  }

  private AuthenticationResult createAuthenticationResult(final Authentication authentication) {
    final AuthenticationResult result = new AuthenticationResult();

    try {
      final Algorithm algorithm = Algorithm.HMAC256("secret");
      final String token = JWT.create().withIssuer("net.brainified").sign(algorithm);
      result.setToken(token);
      return result;
    } catch (final UnsupportedEncodingException exception) {
      // UTF-8 encoding not supported
    } catch (final JWTCreationException exception) {
      // Invalid Signing configuration / Couldn't convert Claims.
    }
    throw new RuntimeException();

  }

}
