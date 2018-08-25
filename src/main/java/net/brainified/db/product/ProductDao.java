package net.brainified.db.product;

import org.springframework.data.domain.Pageable;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductDao {

  Mono<ProductDocument> save(ProductDocument productDocument);

  Mono<ProductDocument> findById(String id);

  Flux<ProductDocument> findAllBy(Pageable pageable);

  Mono<Void> deleteById(String id);

}