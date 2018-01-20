package net.brainified.domain.authentication;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import net.brainified.db.UserDetailsDocument;
import net.brainified.db.UserDetailsRepository;
import reactor.core.publisher.Mono;

@Service
final class UserServiceImpl implements UserService {

  private static final String DEFAULT_ROLE = "USER";

  private final PasswordEncoder passwordEncoder;

  private final UserDetailsRepository repository;

  public UserServiceImpl(final PasswordEncoder passwordEncoder, final UserDetailsRepository repository) {
    this.passwordEncoder = passwordEncoder;
    this.repository = repository;
  }

  @Override
  public Mono<User> addUser(final LoginData loginData) {
    final UserDetailsDocument document = createDocument(loginData);
    return repository.save(document).map(this::createUser);
  }

  private UserDetailsDocument createDocument(final LoginData loginData) {
    final UserDetailsDocument document = new UserDetailsDocument();
    document.setUsername(loginData.getUsername());
    document.setPasswordHash(passwordEncoder.encode(loginData.getPassword()));
    document.setRole(DEFAULT_ROLE);
    return document;
  }

  private User createUser(final UserDetailsDocument savedDocument) {
    final User user = new User();
    user.setId(savedDocument.getId());
    user.setCreatedAt(savedDocument.getCreatedAt());
    user.setUsername(savedDocument.getUsername());
    user.setRole(savedDocument.getRole());
    return user;
  }

}
