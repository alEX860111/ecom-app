package net.brainified.db;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

import reactor.core.publisher.Flux;

public interface ProductRepository extends ReactiveSortingRepository<ProductDocument, String> {

  Flux<ProductDocument> findAllBy(final Pageable pageable);

}
