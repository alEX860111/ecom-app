package net.brainified.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.brainified.domain.authentication.AuthenticationData;
import net.brainified.domain.authentication.AuthenticationResult;
import net.brainified.domain.authentication.AuthenticationService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/login")
final class AuthenticationController {

  private final AuthenticationService authenticationService;

  public AuthenticationController(final AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @PostMapping
  public Mono<ResponseEntity<AuthenticationResult>> addUser(@RequestBody final AuthenticationData credentials) {
    return authenticationService.authenticate(credentials).map(result -> ResponseEntity.ok(result))
        .onErrorReturn(AuthenticationException.class, ResponseEntity.status(HttpStatus.FORBIDDEN).build());
  }

}
