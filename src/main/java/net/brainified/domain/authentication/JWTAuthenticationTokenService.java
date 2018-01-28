package net.brainified.domain.authentication;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import reactor.core.publisher.Mono;

@Service
final class JWTAuthenticationTokenService implements AuthenticationTokenService {

  private static final Logger LOGGER = LoggerFactory.getLogger(JWTAuthenticationTokenService.class);

  private final ReactiveUserDetailsService userDetailsService;

  private final PasswordEncoder passwordEncoder;

  public JWTAuthenticationTokenService(final ReactiveUserDetailsService userDetailsService, final PasswordEncoder passwordEncoder) {
    this.userDetailsService = userDetailsService;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public Mono<AuthenticationToken> createToken(final LoginData loginData) {
    return userDetailsService.findByUsername(loginData.getUsername())
        .doOnNext(userDetails -> this.comparePasswords(loginData, userDetails))
        .map(this::createAuthenticationToken);
  }

  private void comparePasswords(final LoginData loginData, final UserDetails userDetails) {
    final String rawPassword = loginData.getPassword();
    final String encodedPassword = userDetails.getPassword();

    if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
      throw new BadCredentialsException("Bad Credentials");
    }
  }

  private AuthenticationToken createAuthenticationToken(final UserDetails userDetails) {
    try {
      final Algorithm algorithm = Algorithm.HMAC256("secret");

      final String token = JWT.create()
          .withIssuer("net.brainified")
          .withIssuedAt(Date.valueOf(LocalDate.now()))
          .withSubject(userDetails.getUsername())
          .withArrayClaim("roles", getRoles(userDetails))
          .sign(algorithm);

      return new AuthenticationToken(token);
    } catch (final UnsupportedEncodingException | JWTCreationException exception) {
      LOGGER.error(exception.getMessage(), exception);
      throw new RuntimeException(exception);
    }

  }

  private String[] getRoles(final UserDetails userDetails) {
    return userDetails.getAuthorities()
        .stream()
        .map(GrantedAuthority::getAuthority)
        .toArray(String[]::new);
  };

}
