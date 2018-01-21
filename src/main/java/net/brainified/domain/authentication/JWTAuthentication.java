package net.brainified.domain.authentication;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

final class JWTAuthentication implements Authentication {

  private static final long serialVersionUID = 7068816552270457420L;

  private final String token;

  public JWTAuthentication(final String token) {
    this.token = token;
  }

  @Override
  public String getName() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    throw new UnsupportedOperationException();

  }

  @Override
  public Object getCredentials() {
    return token;
  }

  @Override
  public Object getDetails() {
    throw new UnsupportedOperationException();

  }

  @Override
  public Object getPrincipal() {
    throw new UnsupportedOperationException();

  }

  @Override
  public boolean isAuthenticated() {
    return false;
  }

  @Override
  public void setAuthenticated(final boolean isAuthenticated) throws IllegalArgumentException {
    throw new UnsupportedOperationException();
  }

}
