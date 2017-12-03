package net.brainified.domain;

import org.springframework.stereotype.Service;

import net.brainified.db.Product;
import net.brainified.db.ProductRepository;
import reactor.core.publisher.Flux;

@Service
final class ProductServiceImpl implements ProductService {

  private ProductRepository productRepository;

  public ProductServiceImpl(final ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public Flux<Product> getProducts() {
    return productRepository.findAll();
  }

}
