package net.brainified.domain.authentication;

import com.auth0.jwt.algorithms.Algorithm;

interface JWTAlgorithmService {

  Algorithm getAlgorithm();

}
