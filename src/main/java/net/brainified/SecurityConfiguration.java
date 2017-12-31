package net.brainified;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SecurityConfiguration {

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(final ServerHttpSecurity http) {
    http.csrf().disable()
      .authorizeExchange()
        .anyExchange().authenticated()
        .and()
      .httpBasic().and()
      .formLogin();
    return http.build();
  }

}