package net.brainified.domain.product;

import org.springframework.data.domain.Pageable;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

  Mono<Product> addProduct(ProductCreationData productCreationData);

  Flux<Product> getProducts(Pageable pageable);

  Mono<Product> getProduct(String productId);

  Mono<Product> updateProduct(String productId, ProductCreationData productCreationData);

  Mono<Product> deleteProduct(String productId);

}
