package net.brainified.domain.authentication;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import net.brainified.db.UserDetailsDocument;
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
    return repository.findByUsername(username).map(this::createUserDetails);
  }

  private UserDetails createUserDetails(final UserDetailsDocument document) {
    return User.builder()
        .username(document.getUsername())
        .password(document.getPasswordHash())
        .roles(document.getRole())
        .build();
  }

}
