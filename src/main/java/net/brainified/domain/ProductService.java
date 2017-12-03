package net.brainified.domain;

import net.brainified.db.Product;
import reactor.core.publisher.Flux;

public interface ProductService {

  public Flux<Product> getProducts();

}
