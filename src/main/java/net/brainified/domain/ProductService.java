package net.brainified.domain;

import org.springframework.data.domain.Pageable;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

  Mono<Product> addProduct(ProductCoreData productCoreData);

  Flux<Product> getProducts(Pageable pageable);

  Mono<Product> getProduct(String productId);

  Mono<Product> updateProduct(String productId, ProductCoreData productCoreData);

  Mono<Product> deleteProduct(String productId);

}
