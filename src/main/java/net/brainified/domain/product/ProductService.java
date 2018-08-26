package net.brainified.domain.product;

import org.springframework.data.domain.Pageable;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

  Mono<Product> addProduct(ProductWriteCommand productWriteCommand);

  Flux<Product> getProducts(Pageable pageable);

  Mono<Product> getProduct(String productId);

  Mono<Product> updateProduct(String productId, ProductWriteCommand productWriteCommand);

  Mono<Product> deleteProduct(String productId);

}
