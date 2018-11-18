package net.brainified.rest.authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ServerWebExchange;

@ExtendWith(MockitoExtension.class)
class JWTAuthenticationConverterTest {

  private JWTAuthenticationConverter converter;

  @Mock
  private ServerWebExchange exchange;

  @Mock
  private ServerHttpRequest serverHttpRequest;

  private HttpHeaders headers;

  @BeforeEach
  public void setUp() {
    converter = new JWTAuthenticationConverter();

    headers = new HttpHeaders();
    when(serverHttpRequest.getHeaders()).thenReturn(headers);

    when(exchange.getRequest()).thenReturn(serverHttpRequest);
  }

  @Test
  void test() {
    headers.put(HttpHeaders.AUTHORIZATION, Arrays.asList("Bearer token"));
    final Authentication authentication = converter.apply(exchange).block();
    assertEquals("token", authentication.getCredentials());
  }

  @Test
  void test_NoToken() {
    headers.put(HttpHeaders.AUTHORIZATION, Arrays.asList("Bearer "));
    final Authentication authentication = converter.apply(exchange).block();
    assertNull(authentication);
  }

  @Test
  void test_NoBearerPrefix() {
    headers.put(HttpHeaders.AUTHORIZATION, Arrays.asList("token"));
    final Authentication authentication = converter.apply(exchange).block();
    assertNull(authentication);
  }

  @Test
  void test_AuthorizationHeader() {
    final Authentication authentication = converter.apply(exchange).block();
    assertNull(authentication);
  }

}
