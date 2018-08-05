package net.brainified.rest.authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import net.brainified.MockitoExtension;
import net.brainified.rest.authentication.AuthenticationToken;
import net.brainified.rest.authentication.JWTAlgorithmService;
import net.brainified.rest.authentication.JWTAuthenticationTokenService;
import net.brainified.rest.authentication.LoginData;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class JWTAuthenticationTokenServiceTest {

  private static final String ENCODED_PASSWORD = "ENCODED_PASSWORD";

  private static final String ROLE = "ADMIN";

  private static final String USER = "USER";

  private static final String PASSWORD = "PASSWORD";

  @Mock
  private ReactiveUserDetailsService userDetailsService;

  @Mock
  private PasswordEncoder passwordEncoder;
  
  @Mock
  private JWTAlgorithmService jWTAlgorithmService;

  @InjectMocks
  private JWTAuthenticationTokenService service;

  private LoginData loginData;

  @BeforeEach
  public void setUp() throws UnsupportedEncodingException {
    loginData = new LoginData();
    loginData.setUsername(USER);
    loginData.setPassword(PASSWORD);
    
    when(jWTAlgorithmService.getAlgorithm()).thenReturn(Algorithm.HMAC256("xyz"));
  }

  @Test
  void createToken_UsernameNotFound() {
    when(userDetailsService.findByUsername(USER)).thenReturn(Mono.empty());

    final Mono<AuthenticationToken> userDetails = service.createToken(loginData);

    assertFalse(userDetails.hasElement().block());

    verify(userDetailsService).findByUsername(USER);
    verifyZeroInteractions(passwordEncoder);
  }

  @Test
  void createToken_BadCredentials(@Mock final UserDetails userDetails) {
    when(userDetails.getPassword()).thenReturn(ENCODED_PASSWORD);
    when(userDetailsService.findByUsername(USER)).thenReturn(Mono.just(userDetails));

    when(passwordEncoder.matches(PASSWORD, ENCODED_PASSWORD)).thenReturn(false);

    assertThrows(BadCredentialsException.class, () -> service.createToken(loginData).block());

    verify(userDetailsService).findByUsername(USER);
    verify(passwordEncoder).matches(PASSWORD, ENCODED_PASSWORD);
  }

  @Test
  void createToken(@Mock final UserDetails userDetails, @Mock final GrantedAuthority grantedAuthority) {
    when(grantedAuthority.getAuthority()).thenReturn(ROLE);
    doReturn(Arrays.asList(grantedAuthority)).when(userDetails).getAuthorities();

    when(userDetails.getUsername()).thenReturn(USER);
    when(userDetails.getPassword()).thenReturn(ENCODED_PASSWORD);
    when(userDetailsService.findByUsername(USER)).thenReturn(Mono.just(userDetails));

    when(passwordEncoder.matches(PASSWORD, ENCODED_PASSWORD)).thenReturn(true);

    final AuthenticationToken authenticationToken = service.createToken(loginData).block();

    final String token = authenticationToken.getToken();
    final DecodedJWT jwt = JWT.decode(token);

    assertEquals("net.brainified", jwt.getIssuer());
    assertNotNull(jwt.getIssuedAt());
    assertEquals(USER, jwt.getSubject());
    assertEquals(Arrays.asList(ROLE), jwt.getClaim("roles").asList(String.class));

    verify(userDetailsService).findByUsername(USER);
    verify(passwordEncoder).matches(PASSWORD, ENCODED_PASSWORD);
  }

}
