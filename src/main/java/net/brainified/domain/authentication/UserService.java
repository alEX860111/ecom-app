package net.brainified.domain.authentication;

import reactor.core.publisher.Mono;

public interface UserService {

  Mono<User> addUser(LoginData loginData);

}
