package net.brainified.domain;

import org.springframework.data.domain.Pageable;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

  Mono<Product> addProduct(Product product);

  Flux<Product> getProducts(Pageable pageable);

  Mono<Product> getProduct(String productId);

  Mono<Product> updateProduct(Product product);

  Mono<Product> deleteProduct(String productId);

}
