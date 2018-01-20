package net.brainified.domain.authentication;

public final class AuthenticationResult {

  private final String token;

  public AuthenticationResult(final String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }

}
