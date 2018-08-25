package net.brainified.db.product;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
final class ProductDaoImpl implements ProductDao {

  private final ProductRepository productRepository;

  public ProductDaoImpl(final ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public Flux<ProductDocument> findAllBy(final Pageable pageable) {
    return productRepository.findAllBy(pageable);
  }

  @Override
  public Mono<ProductDocument> save(final ProductDocument productDocument) {
    return productRepository.save(productDocument);
  }

  @Override
  public Mono<ProductDocument> findById(final String id) {
    return productRepository.findById(id);
  }

  @Override
  public Mono<Void> deleteById(final String id) {
    return productRepository.deleteById(id);
  }

}
