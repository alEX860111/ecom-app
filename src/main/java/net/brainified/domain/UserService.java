package net.brainified.domain;

import reactor.core.publisher.Mono;

public interface UserService {

  Mono<User> addUser(UserCreationData userCreationData);

}
