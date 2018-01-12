package net.brainified.domain.authentication;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
final class AuthenticationManagerImpl implements ReactiveAuthenticationManager {

  private final ReactiveUserDetailsService userDetailsService;

  private final PasswordEncoder passwordEncoder;

  public AuthenticationManagerImpl(final ReactiveUserDetailsService userDetailsService,
      final PasswordEncoder passwordEncoder) {
    this.userDetailsService = userDetailsService;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public Mono<Authentication> authenticate(final Authentication authentication) throws AuthenticationException {
    return userDetailsService.findByUsername(authentication.getName())
        .map(userDetails -> this.createAuthentication(authentication, userDetails));
  }

  private Authentication createAuthentication(final Authentication authentication, final UserDetails userDetails) {
    final CharSequence rawPassword = (CharSequence) authentication.getCredentials();
    final String encodedPassword = userDetails.getPassword();

    if (passwordEncoder.matches(rawPassword, encodedPassword)) {
      return new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials(),
          userDetails.getAuthorities());
    }

    throw new BadCredentialsException("Bad Credentials");
  }

}
