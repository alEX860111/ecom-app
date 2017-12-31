package net.brainified.domain;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import net.brainified.db.UserDetailsDocument;
import net.brainified.db.UserDetailsRepository;
import reactor.core.publisher.Mono;

@Service
final class UserServiceImpl implements UserService {

  private final UserDetailsRepository repository;

  public UserServiceImpl(final UserDetailsRepository repository) {
    this.repository = repository;
  }

  @Override
  public Mono<User> addUser(final UserCreationData userCreationData) {
    final String role = "USER";

    final UserDetails userDetails = org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder()
        .username(userCreationData.getUsername()).password(userCreationData.getPassword()).roles(role).build();

    final UserDetailsDocument document = new UserDetailsDocument();
    document.setUsername(userDetails.getUsername());
    document.setPasswordHash(userDetails.getPassword());
    document.setRole(role);

    return repository.save(document).map(savedDocument -> {
      final User user = new User();
      user.setId(savedDocument.getId());
      user.setCreatedAt(savedDocument.getCreatedAt());
      user.setUsername(savedDocument.getUsername());
      user.setRole(savedDocument.getRole());
      return user;
    });

  }

}
