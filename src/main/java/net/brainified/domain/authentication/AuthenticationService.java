package net.brainified.domain.authentication;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import reactor.core.publisher.Mono;

interface AuthenticationService {

  Mono<UserDetails> authenticate(LoginData loginData) throws AuthenticationException;

}
