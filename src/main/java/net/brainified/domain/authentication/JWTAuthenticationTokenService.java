package net.brainified.domain.authentication;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import reactor.core.publisher.Mono;

@Service
final class JWTAuthenticationTokenService implements AuthenticationTokenService {

  private final ReactiveUserDetailsService userDetailsService;

  private final PasswordEncoder passwordEncoder;

  private final JWTAlgorithmService jWTAlgorithmService;

  public JWTAuthenticationTokenService(
      final ReactiveUserDetailsService userDetailsService,
      final PasswordEncoder passwordEncoder,
      final JWTAlgorithmService jWTAlgorithmService) {
    this.userDetailsService = userDetailsService;
    this.passwordEncoder = passwordEncoder;
    this.jWTAlgorithmService = jWTAlgorithmService;
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
    final Algorithm algorithm = jWTAlgorithmService.getAlgorithm();

    final String token = JWT.create()
        .withIssuer("net.brainified")
        .withIssuedAt(Date.valueOf(LocalDate.now()))
        .withSubject(userDetails.getUsername())
        .withArrayClaim("roles", getRoles(userDetails))
        .sign(algorithm);

    return new AuthenticationToken(token);
  }

  private String[] getRoles(final UserDetails userDetails) {
    return userDetails.getAuthorities()
        .stream()
        .map(GrantedAuthority::getAuthority)
        .toArray(String[]::new);
  };

}
