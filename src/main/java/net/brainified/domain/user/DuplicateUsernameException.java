package net.brainified.domain.user;

public final class DuplicateUsernameException extends RuntimeException {

  public DuplicateUsernameException(final String username) {
    super(String.format("username '%s' already exists", username));
  }

  private static final long serialVersionUID = 1L;

}
