package net.brainified.rest.authentication;

import reactor.core.publisher.Mono;

public interface AuthenticationTokenService {

  Mono<AuthenticationToken> createToken(LoginData loginData);

}
