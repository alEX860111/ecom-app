package net.brainified.domain.authentication;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import net.brainified.MockitoExtension;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
final class AuthenticationServiceImplTest {

  private static final String USERNAME = "USERNAME";

  @Mock
  private ReactiveUserDetailsService userDetailsService;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private AuthenticationServiceImpl authenticationService;

  private LoginData loginData;

  @BeforeEach
  public void setUp() {
    loginData = new LoginData();
    loginData.setUsername(USERNAME);
  }

  @Test
  void authenticate_UsernameNotFound() {
    when(userDetailsService.findByUsername(USERNAME)).thenReturn(Mono.empty());

    final Mono<UserDetails> userDetails = authenticationService.authenticate(loginData);

    assertFalse(userDetails.hasElement().block());

    verify(userDetailsService).findByUsername(USERNAME);
    verifyZeroInteractions(passwordEncoder);
  }

  @Test
  void authenticate_BadCredentials() {
    loginData.setPassword("PASSWORD");

    final UserDetails userDetails = Mockito.mock(UserDetails.class);
    when(userDetails.getPassword()).thenReturn("ENCODED_PASSWORD");
    when(userDetailsService.findByUsername(USERNAME)).thenReturn(Mono.just(userDetails));

    when(passwordEncoder.matches("PASSWORD", "ENCODED_PASSWORD")).thenReturn(false);

    assertThrows(BadCredentialsException.class, () -> authenticationService.authenticate(loginData).block());

    verify(userDetailsService).findByUsername(USERNAME);
    verify(passwordEncoder).matches("PASSWORD", "ENCODED_PASSWORD");
  }

  @Test
  void authenticate(@Mock final GrantedAuthority grantedAuthority) {
    loginData.setPassword("PASSWORD");

    final UserDetails userDetails = Mockito.mock(UserDetails.class);
    when(userDetails.getPassword()).thenReturn("ENCODED_PASSWORD");

    when(grantedAuthority.getAuthority()).thenReturn("ADMIN");

    doReturn(Arrays.asList(grantedAuthority)).when(userDetails).getAuthorities();
    when(userDetailsService.findByUsername(USERNAME)).thenReturn(Mono.just(userDetails));

    when(passwordEncoder.matches("PASSWORD", "ENCODED_PASSWORD")).thenReturn(true);

    final UserDetails userDetailsResult = authenticationService.authenticate(loginData).block();

    assertSame(userDetails, userDetailsResult);

    verify(userDetailsService).findByUsername(USERNAME);
    verify(passwordEncoder).matches("PASSWORD", "ENCODED_PASSWORD");
  }

}
