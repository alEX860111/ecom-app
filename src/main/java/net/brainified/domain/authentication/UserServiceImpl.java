package net.brainified.domain.authentication;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import net.brainified.db.UserDetailsDocument;
import net.brainified.db.UserDetailsRepository;
import reactor.core.publisher.Mono;

@Service
final class UserServiceImpl implements UserService {

  private final UserConverter userConverter;

  private final UserDetailsRepository repository;

  public UserServiceImpl(final UserConverter userConverter, final UserDetailsRepository repository) {
    this.userConverter = userConverter;
    this.repository = repository;
  }

  @Override
  public Mono<User> addUser(final LoginData loginData) {
    final UserDetailsDocument document = userConverter.createDocument(loginData);
    return repository.save(document)
        .map(userConverter::createUser)
        .onErrorMap(DuplicateKeyException.class, e -> new DuplicateUsernameException(loginData.getUsername()));
  }

}
