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
  public Mono<User> addUser(final AuthenticationRequest credentials) {
    final UserDetailsDocument document = createDocument(credentials);
    return repository.save(document).map(this::createUser);
  }

  private UserDetailsDocument createDocument(final AuthenticationRequest credentials) {
    final UserDetailsDocument document = new UserDetailsDocument();
    document.setUsername(credentials.getUsername());
    document.setPasswordHash(passwordEncoder.encode(credentials.getPassword()));
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
