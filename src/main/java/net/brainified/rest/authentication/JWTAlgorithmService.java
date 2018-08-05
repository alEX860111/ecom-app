package net.brainified.rest.authentication;

import com.auth0.jwt.algorithms.Algorithm;

interface JWTAlgorithmService {

  Algorithm getAlgorithm();

}
