package net.brainified;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SecurityConfiguration {

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(final ServerHttpSecurity http) {
    http.httpBasic().disable();
    http.formLogin().disable();
    http.csrf().disable();
    http.logout().disable();

    http.authorizeExchange().pathMatchers("/login").permitAll();
    http.authorizeExchange().pathMatchers("/users").permitAll();
    http.authorizeExchange().anyExchange().authenticated();
    return http.build();
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}