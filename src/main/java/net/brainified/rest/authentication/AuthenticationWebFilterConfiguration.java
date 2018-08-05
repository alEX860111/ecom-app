package net.brainified.rest.authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

@Configuration
class AuthenticationWebFilterConfiguration {

  private final ReactiveAuthenticationManager reactiveAuthenticationManager;

  private final JWTAuthenticationConverter authenticationConverter;

  public AuthenticationWebFilterConfiguration(
      final ReactiveAuthenticationManager reactiveAuthenticationManager,
      final JWTAuthenticationConverter authenticationConverter) {
    this.reactiveAuthenticationManager = reactiveAuthenticationManager;
    this.authenticationConverter = authenticationConverter;
  }

  @Bean
  public AuthenticationWebFilter createAuthenticationWebFilter() {
    final AuthenticationWebFilter filter = new AuthenticationWebFilter(reactiveAuthenticationManager);
    filter.setAuthenticationConverter(authenticationConverter);
    return filter;
  }

}
