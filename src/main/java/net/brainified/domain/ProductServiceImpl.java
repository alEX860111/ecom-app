package net.brainified.domain;

import org.springframework.stereotype.Service;

import net.brainified.db.Product;
import net.brainified.db.ProductRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

  @Override
  public Mono<Product> getProduct(final String productId) {
    return productRepository.findById(productId);
  }

  @Override
  public Mono<Product> addProduct(Product product) {
    return productRepository.save(product);
  }

}
