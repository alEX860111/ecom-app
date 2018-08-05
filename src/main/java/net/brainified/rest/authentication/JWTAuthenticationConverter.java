package net.brainified.rest.authentication;

import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Service
final class JWTAuthenticationConverter implements Function<ServerWebExchange, Mono<Authentication>> {

  private static final Pattern PATTERN = Pattern.compile("Bearer (.+)");

  @Override
  public Mono<Authentication> apply(final ServerWebExchange exchange) {
    final ServerHttpRequest request = exchange.getRequest();

    final HttpHeaders headers = request.getHeaders();

    final String authorization = headers.getFirst(HttpHeaders.AUTHORIZATION);

    if (Objects.isNull(authorization)) {
      return Mono.empty();
    }

    final Matcher matcher = PATTERN.matcher(authorization);

    if (!matcher.matches()) {
      return Mono.empty();
    }

    return Mono.just(new JWTAuthentication(matcher.group(1)));
  }

}
