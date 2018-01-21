package net.brainified.domain.authentication;

import java.io.UnsupportedEncodingException;
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

  @Override
  public Mono<Authentication> authenticate(final Authentication authentication) throws AuthenticationException {
    final String token = (String) authentication.getCredentials();

    try {
      final Algorithm algorithm = Algorithm.HMAC256("secret");
      final JWTVerifier verifier = JWT.require(algorithm)
          .withIssuer("net.brainified")
          .build();

      final DecodedJWT jwt = verifier.verify(token);

      final List<SimpleGrantedAuthority> authorities = jwt.getClaim("roles").asList(SimpleGrantedAuthority.class);
      return Mono.just(new UsernamePasswordAuthenticationToken(jwt.getSubject(), token, authorities));

    } catch (final UnsupportedEncodingException exception) {
    } catch (final JWTVerificationException exception) {
    }
    throw new BadCredentialsException("Token could not be verified");
  }

}
