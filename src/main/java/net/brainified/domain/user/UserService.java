package net.brainified.domain.user;

import reactor.core.publisher.Mono;

public interface UserService {

  Mono<User> addUser(AddUserRequest addUserRequest);

}
