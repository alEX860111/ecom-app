package net.brainified.domain;

import org.springframework.data.domain.Pageable;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

  public Flux<Product> getProducts(Pageable pageable);

  public Mono<Product> getProduct(String productId);

  public Mono<Product> addProduct(Product product);

}
