package net.brainified.domain.authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

@Configuration
class AuthenticationWebFilterConfiguration {

  private final ReactiveAuthenticationManager reactiveAuthenticationManager;

  private final JWTAuthenticationConverter authenticationConverter;

  public AuthenticationWebFilterConfiguration(final ReactiveAuthenticationManager reactiveAuthenticationManager,
      final JWTAuthenticationConverter authenticationConverter) {
    this.reactiveAuthenticationManager = reactiveAuthenticationManager;
    this.authenticationConverter = authenticationConverter;
  }

  @Bean
  public AuthenticationWebFilter createAuthenticationWebFilter() {
    final AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(reactiveAuthenticationManager);
    authenticationWebFilter.setAuthenticationConverter(authenticationConverter);
    return authenticationWebFilter;
  }

}
