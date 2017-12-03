package net.brainified;

import java.util.Arrays;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

@Service
final class ProductServiceImpl implements ProductService {

  @Override
  public Flux<Product> getProducts() {
    final Product iphone = new Product();
    iphone.setName("iphone");
    iphone.setPrice(1199.00d);

    final Product razr = new Product();
    razr.setName("razr");
    razr.setPrice(499.00d);

    return Flux.fromIterable(Arrays.asList(iphone, razr));
  }

}
