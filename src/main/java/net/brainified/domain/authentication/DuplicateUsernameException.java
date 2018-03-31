package net.brainified.domain.authentication;

public class DuplicateUsernameException extends RuntimeException {

  public DuplicateUsernameException(final String username) {
    super(String.format("username '%s' does already exist", username));
  }

  private static final long serialVersionUID = 1L;

}
