package net.brainified.domain;

import net.brainified.db.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

  public Flux<Product> getProducts();

  public Mono<Product> getProduct(String productId);

  public Mono<Product> addProduct(Product product);

}
