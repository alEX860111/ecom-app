package net.brainified.domain.products;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.brainified.db.ProductDocument;
import net.brainified.db.ProductRepository;
import net.brainified.domain.products.Product;
import net.brainified.domain.products.ProductCoreData;
import net.brainified.domain.products.ProductService;
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
    final ProductDocument productDocument = productConverter.createProductDocument(productCoreData);
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
  public Mono<Product> updateProduct(final String productId, final ProductCoreData productCoreData) {
    return productRepository.findById(productId).flatMap(document -> {
      final ProductDocument updatedDocument = productConverter.updateProductDocument(document, productCoreData);
      return productRepository.save(updatedDocument).map(productConverter::createProduct);
    });
  }

  @Override
  public Mono<Product> deleteProduct(final String productId) {
    return getProduct(productId).delayUntil(product -> productRepository.deleteById(product.getId()));
  }

}
