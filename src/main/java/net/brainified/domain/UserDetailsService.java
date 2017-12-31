package net.brainified.domain;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import net.brainified.db.UserDetailsRepository;
import reactor.core.publisher.Mono;

@Service
final class UserDetailsService implements ReactiveUserDetailsService {

  private final UserDetailsRepository repository;

  public UserDetailsService(final UserDetailsRepository repository) {
    this.repository = repository;
  }

  @Override
  public Mono<UserDetails> findByUsername(final String username) {
    return repository.findByUsername(username).map(userDetailsDocument -> {
      return User.builder().username(userDetailsDocument.getUsername()).password(userDetailsDocument.getPasswordHash())
          .roles(userDetailsDocument.getRole()).build();
    });
  }

}
