package net.brainified;

import reactor.core.publisher.Flux;

public interface ProductService {

  public Flux<Product> getProducts();

}
