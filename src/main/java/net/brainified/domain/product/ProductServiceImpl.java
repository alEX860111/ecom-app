package net.brainified.domain.product;

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
  public Mono<Product> addProduct(final ProductAttributes productAttributes) {
    final ProductDocument productDocument = productConverter.createProductDocument(productAttributes);
    return productRepository.save(productDocument).map(productConverter::createProduct);
  }

  @Override
  public Flux<Product> getProducts(final Pageable pageable) {
    return productRepository.findAllBy(pageable).map(productConverter::createProduct);
  }

  @Override
  public Mono<Product> getProduct(final String productId) {
    return productRepository.findById(productId).map(productConverter::createProduct);
  }

  @Override
  public Mono<Product> updateProduct(final String productId, final ProductAttributes productAttributes) {
    return productRepository.findById(productId).flatMap(document -> {
      final ProductDocument updatedDocument = productConverter.updateProductDocument(document, productAttributes);
      return productRepository.save(updatedDocument).map(productConverter::createProduct);
    });
  }

  @Override
  public Mono<Product> deleteProduct(final String productId) {
    return getProduct(productId).delayUntil(product -> productRepository.deleteById(product.getId()));
  }

}
