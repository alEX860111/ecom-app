package net.brainified.rest.authentication;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/login")
final class AuthenticationController {

  private final AuthenticationTokenService authenticationTokenService;

  public AuthenticationController(final AuthenticationTokenService authenticationTokenService) {
    this.authenticationTokenService = authenticationTokenService;
  }

  @PostMapping
  public Mono<ResponseEntity<AuthenticationToken>> login(@RequestBody final LoginData loginData) {
    return authenticationTokenService.createToken(loginData)
        .map(result -> ResponseEntity.ok(result))
        .onErrorReturn(AuthenticationException.class, ResponseEntity.status(HttpStatus.FORBIDDEN).build())
        .defaultIfEmpty(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
  }

}
