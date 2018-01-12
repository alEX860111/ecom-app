package net.brainified.domain.authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import net.brainified.MockitoExtension;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
final class AuthenticationManagerImplTest {

  private static final String USERNAME = "USERNAME";

  @Mock
  private ReactiveUserDetailsService userDetailsService;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private AuthenticationManagerImpl authenticationManager;

  @Mock
  private Authentication authentication;

  @BeforeEach
  public void setUp() {
    when(authentication.getName()).thenReturn(USERNAME);
  }

  @Test
  void authenticate_UsernameNotFound() {
    when(userDetailsService.findByUsername(USERNAME)).thenReturn(Mono.empty());

    final Mono<Authentication> authenticate = authenticationManager.authenticate(authentication);

    assertFalse(authenticate.hasElement().block());

    verify(userDetailsService).findByUsername(USERNAME);
    verifyZeroInteractions(passwordEncoder);
  }

  @Test
  void authenticate_BadCredentials() {
    when(authentication.getCredentials()).thenReturn("PASSWORD");

    final UserDetails userDetails = Mockito.mock(UserDetails.class);
    when(userDetails.getPassword()).thenReturn("ENCODED_PASSWORD");
    when(userDetailsService.findByUsername(USERNAME)).thenReturn(Mono.just(userDetails));

    when(passwordEncoder.matches("PASSWORD", "ENCODED_PASSWORD")).thenReturn(false);

    final Mono<Authentication> authenticate = authenticationManager.authenticate(authentication);

    authenticate.subscribe(auth -> fail("No Success expected."), Assertions::assertNotNull);

    verify(userDetailsService).findByUsername(USERNAME);
    verify(passwordEncoder).matches("PASSWORD", "ENCODED_PASSWORD");
  }

  @Test
  void authenticate() {
    when(authentication.getCredentials()).thenReturn("PASSWORD");

    final UserDetails userDetails = Mockito.mock(UserDetails.class);
    when(userDetails.getPassword()).thenReturn("ENCODED_PASSWORD");

    final GrantedAuthority grantedAuthority = Mockito.mock(GrantedAuthority.class);
    when(grantedAuthority.getAuthority()).thenReturn("ADMIN");

    doReturn(Arrays.asList(grantedAuthority)).when(userDetails).getAuthorities();
    when(userDetailsService.findByUsername(USERNAME)).thenReturn(Mono.just(userDetails));

    when(passwordEncoder.matches("PASSWORD", "ENCODED_PASSWORD")).thenReturn(true);

    final Mono<Authentication> authenticate = authenticationManager.authenticate(authentication);

    authenticate.subscribe(auth -> {
      assertTrue(auth.isAuthenticated());
      assertEquals(USERNAME, auth.getName());
      assertEquals("PASSWORD", auth.getCredentials());
      assertEquals(1, auth.getAuthorities().size());
      final Optional<? extends GrantedAuthority> authority = auth.getAuthorities().stream().findFirst();
      assertEquals("ADMIN", authority.get().getAuthority());
    }, Assertions::fail);

    verify(userDetailsService).findByUsername(USERNAME);
    verify(passwordEncoder).matches("PASSWORD", "ENCODED_PASSWORD");
  }

}
