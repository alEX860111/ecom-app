package net.brainified.domain.authentication;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import net.brainified.db.UserDetailsDocument;

@Service
final class UserConverterImpl implements UserConverter {

  private static final String DEFAULT_ROLE = "USER";

  private final PasswordEncoder passwordEncoder;

  public UserConverterImpl(final PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserDetailsDocument createDocument(final LoginData loginData) {
    final UserDetailsDocument document = new UserDetailsDocument();
    document.setUsername(loginData.getUsername());
    document.setPasswordHash(passwordEncoder.encode(loginData.getPassword()));
    document.setRole(DEFAULT_ROLE);
    return document;
  }

  @Override
  public User createUser(final UserDetailsDocument document) {
    final User user = new User();
    user.setId(document.getId());
    user.setCreatedAt(document.getCreatedAt());
    user.setUsername(document.getUsername());
    user.setRole(document.getRole());
    return user;
  }

}