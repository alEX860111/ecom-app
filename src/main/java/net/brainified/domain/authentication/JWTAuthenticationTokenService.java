package net.brainified.domain.authentication;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.time.LocalDate;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import reactor.core.publisher.Mono;

@Service
final class JWTAuthenticationTokenService implements AuthenticationTokenService {

  private final AuthenticationService authenticationService;

  public JWTAuthenticationTokenService(final AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @Override
  public Mono<AuthenticationToken> createToken(final LoginData loginData) {
    return authenticationService.authenticate(loginData)
        .map(this::createAuthenticationResult);
  }

  private AuthenticationToken createAuthenticationResult(final UserDetails userDetails) {
    try {
      final Algorithm algorithm = Algorithm.HMAC256("secret");

      final String token = JWT.create()
          .withIssuer("net.brainified")
          .withIssuedAt(Date.valueOf(LocalDate.now()))
          .withSubject(userDetails.getUsername())
          .withArrayClaim("roles", getRoles(userDetails))
          .sign(algorithm);

      return new AuthenticationToken(token);
    } catch (final UnsupportedEncodingException exception) {
      // UTF-8 encoding not supported
    } catch (final JWTCreationException exception) {
      // Invalid Signing configuration / Couldn't convert Claims.
    }
    throw new RuntimeException();

  }

  private String[] getRoles(final UserDetails userDetails) {
    return userDetails.getAuthorities()
        .stream()
        .map(GrantedAuthority::getAuthority)
        .toArray(String[]::new);
  };

}
