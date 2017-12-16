package net.brainified.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.brainified.db.ProductDocument;
import net.brainified.db.ProductRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
final class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  private final ProductConverter productConverter;

  public ProductServiceImpl(final ProductRepository productRepository, final ProductConverter productConverter) {
    this.productRepository = productRepository;
    this.productConverter = productConverter;
  }

  @Override
  public Mono<Product> addProduct(final Product product) {
    final ProductDocument productDocument = productConverter.convertProductToProductDocument(product);
    return productRepository.save(productDocument).map(productConverter::convertProductDocumentToProduct);
  }

  @Override
  public Flux<Product> getProducts(final Pageable pageable) {
    return productRepository.findAllBy(pageable).map(productConverter::convertProductDocumentToProduct);
  }

  @Override
  public Mono<Product> getProduct(final String productId) {
    return productRepository.findById(productId).map(productConverter::convertProductDocumentToProduct);
  }

  @Override
  public Mono<Product> updateProduct(final Product product) {
    return productRepository.existsById(product.getId()).flatMap(exists -> {
      if (exists) {
        final ProductDocument productDocument = productConverter.convertProductToProductDocument(product);
        return productRepository.save(productDocument).map(productConverter::convertProductDocumentToProduct);
      } else {
        return Mono.empty();
      }
    });
  }

  @Override
  public Mono<Product> deleteProduct(final String productId) {
    return getProduct(productId).delayUntil(product -> productRepository.deleteById(product.getId()));
  }

}
