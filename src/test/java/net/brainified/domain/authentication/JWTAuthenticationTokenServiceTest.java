package net.brainified.domain.authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

import net.brainified.MockitoExtension;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class JWTAuthenticationTokenServiceTest {

  private static final String ROLE = "ADMIN";

  private static final String USER = "USER";

  @Mock
  private ReactiveAuthenticationManager authenticationManager;

  @InjectMocks
  private JWTAuthenticationTokenService service;

  private LoginData loginData;

  @Mock
  private Authentication authentication;

  @BeforeEach
  public void setUp() {
    loginData = new LoginData();
  }

  @Test
  void createToken(@Mock final GrantedAuthority grantedAuthority) {
    when(grantedAuthority.getAuthority()).thenReturn(ROLE);
    doReturn(Arrays.asList(grantedAuthority)).when(authentication).getAuthorities();

    when(authentication.getName()).thenReturn(USER);

    when(authenticationManager.authenticate(any())).thenReturn(Mono.just(authentication));

    final AuthenticationToken authenticationToken = service.createToken(loginData).block();

    final String token = authenticationToken.getToken();
    final DecodedJWT jwt = JWT.decode(token);

    assertEquals("net.brainified", jwt.getIssuer());
    assertNotNull(jwt.getIssuedAt());
    assertEquals(USER, jwt.getSubject());
    assertEquals(Arrays.asList(ROLE), jwt.getClaim("role").asList(String.class));
  }

  @Test()
  void createToken_BadCredentials() {
    when(authenticationManager.authenticate(any())).thenReturn(Mono.error(new BadCredentialsException("oops")));

    assertThrows(BadCredentialsException.class, () -> service.createToken(loginData).block());
  }

}
