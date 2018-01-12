package net.brainified.rest;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import net.brainified.domain.authentication.AuthenticationRequest;
import net.brainified.domain.authentication.User;
import net.brainified.domain.authentication.UserService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
final class UserController {

  private final UserService userService;

  public UserController(final UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  public Mono<ResponseEntity<User>> addUser(@RequestBody final AuthenticationRequest userCreationData,
      final UriComponentsBuilder uriComponentBuilder) {
    return userService.addUser(userCreationData).map(savedUser -> {
      final URI location = URI.create(uriComponentBuilder.path("/").path(savedUser.getId()).toUriString());
      return ResponseEntity.created(location).body(savedUser);
    });
  }

}