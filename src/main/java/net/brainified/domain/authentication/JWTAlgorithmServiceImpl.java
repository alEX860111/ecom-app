package net.brainified.domain.authentication;

import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Service;

import com.auth0.jwt.algorithms.Algorithm;

@Service
final class JWTAlgorithmServiceImpl implements JWTAlgorithmService {

  private static final String SECRET = "secret";

  @Override
  public Algorithm getAlgorithm() {
    try {
      return Algorithm.HMAC256(SECRET);
    } catch (final UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

}
