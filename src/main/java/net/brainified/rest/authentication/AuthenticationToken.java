package net.brainified.rest.authentication;

public final class AuthenticationToken {

  private final String token;

  public AuthenticationToken(final String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }

}
