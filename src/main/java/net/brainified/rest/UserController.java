package net.brainified.rest;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import net.brainified.domain.user.AddUserRequest;
import net.brainified.domain.user.DuplicateUsernameException;
import net.brainified.domain.user.User;
import net.brainified.domain.user.UserService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
final class UserController {

  private final UserService userService;

  public UserController(final UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  public Mono<ResponseEntity<User>> addUser(@RequestBody final AddUserRequest addUserRequest, final UriComponentsBuilder uriComponentBuilder) {
    return userService.addUser(addUserRequest)
        .map(savedUser -> {
          final URI location = uriComponentBuilder
              .path("/users/")
              .path(savedUser.getId())
              .build()
              .toUri();
          return ResponseEntity.created(location).body(savedUser);
        })
        .onErrorReturn(DuplicateUsernameException.class, ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
  }

}
