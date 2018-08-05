package net.brainified.rest.authentication;

import java.util.List;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import reactor.core.publisher.Mono;

@Service
final class JWTAuthenticationManager implements ReactiveAuthenticationManager {
  
  private final JWTAlgorithmService jWTAlgorithmService;

  public JWTAuthenticationManager(final JWTAlgorithmService jWTAlgorithmService) {
    this.jWTAlgorithmService = jWTAlgorithmService;
  }

  @Override
  public Mono<Authentication> authenticate(final Authentication authentication) throws AuthenticationException {
    final String token = (String) authentication.getCredentials();

    final Algorithm algorithm = jWTAlgorithmService.getAlgorithm();

    final JWTVerifier verifier = JWT.require(algorithm)
        .withIssuer("net.brainified")
        .build();

    DecodedJWT jwt;
    try {
      jwt = verifier.verify(token);
    } catch (final JWTVerificationException e) {
      throw new BadCredentialsException("Token could not be verified");
    }

    final List<SimpleGrantedAuthority> authorities = jwt.getClaim("roles").asList(SimpleGrantedAuthority.class);
    return Mono.just(new UsernamePasswordAuthenticationToken(jwt.getSubject(), token, authorities));

  }

}
