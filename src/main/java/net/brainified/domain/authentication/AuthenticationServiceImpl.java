package net.brainified.domain.authentication;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
final class AuthenticationServiceImpl implements AuthenticationService {

  private final ReactiveUserDetailsService userDetailsService;

  private final PasswordEncoder passwordEncoder;

  public AuthenticationServiceImpl(final ReactiveUserDetailsService userDetailsService,
      final PasswordEncoder passwordEncoder) {
    this.userDetailsService = userDetailsService;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public Mono<UserDetails> authenticate(final LoginData loginData) throws AuthenticationException {
    return userDetailsService.findByUsername(loginData.getUsername())
        .map(userDetails -> this.createAuthentication(loginData, userDetails));
  }

  private UserDetails createAuthentication(final LoginData loginData, final UserDetails userDetails) {
    final String rawPassword = loginData.getPassword();
    final String encodedPassword = userDetails.getPassword();

    if (passwordEncoder.matches(rawPassword, encodedPassword)) {
      return userDetails;
    }

    throw new BadCredentialsException("Bad Credentials");
  }

}
