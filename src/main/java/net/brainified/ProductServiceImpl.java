package net.brainified;

import org.springframework.stereotype.Service;

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
