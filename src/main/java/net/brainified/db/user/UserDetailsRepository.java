package net.brainified.db.user;

import org.springframework.data.repository.reactive.ReactiveSortingRepository;

import reactor.core.publisher.Mono;

public interface UserDetailsRepository extends ReactiveSortingRepository<UserDetailsDocument, String> {

  Mono<UserDetailsDocument> findByUsername(String username);

}
