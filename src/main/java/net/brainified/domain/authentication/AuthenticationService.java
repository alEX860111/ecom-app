package net.brainified.domain.authentication;

import reactor.core.publisher.Mono;

public interface AuthenticationService {

  Mono<AuthenticationResult> authenticate(AuthenticationData credentials);

}
