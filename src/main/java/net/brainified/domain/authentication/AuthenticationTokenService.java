package net.brainified.domain.authentication;

import reactor.core.publisher.Mono;

public interface AuthenticationTokenService {

  Mono<AuthenticationToken> createToken(LoginData loginData);

}
