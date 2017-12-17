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
  public Mono<Product> addProduct(final ProductCoreData productCoreData) {
    final ProductDocument productDocument = productConverter.convertProductToProductDocument(productCoreData);
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
  public Mono<Product> updateProduct(final String productId, final ProductCoreData productCoreData) {
    return getProduct(productId).flatMap(product -> {
      final ProductDocument productDocument = productConverter.convertProductToProductDocument(productCoreData);
      productDocument.setId(product.getId());
      productDocument.setCreatedAt(product.getCreatedAt());
      return productRepository.save(productDocument).map(productConverter::convertProductDocumentToProduct);
    });
  }

  @Override
  public Mono<Product> deleteProduct(final String productId) {
    return getProduct(productId).delayUntil(product -> productRepository.deleteById(product.getId()));
  }

}
