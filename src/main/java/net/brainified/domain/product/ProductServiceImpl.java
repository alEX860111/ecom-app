package net.brainified.domain.product;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.brainified.db.product.ProductDao;
import net.brainified.db.product.ProductDocument;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
final class ProductServiceImpl implements ProductService {

  private final ProductDao productDao;

  private final ProductConverter productConverter;

  public ProductServiceImpl(final ProductDao productDao, final ProductConverter productConverter) {
    this.productDao = productDao;
    this.productConverter = productConverter;
  }

  @Override
  public Mono<Product> addProduct(final ProductCreationData productCreationData) {
    final ProductDocument productDocument = productConverter.createProductDocument(productCreationData);
    return productDao.save(productDocument).map(productConverter::createProduct);
  }

  @Override
  public Flux<Product> getProducts(final Pageable pageable) {
    return productDao.findAllBy(pageable).map(productConverter::createProduct);
  }

  @Override
  public Mono<Product> getProduct(final String productId) {
    return productDao.findById(productId).map(productConverter::createProduct);
  }

  @Override
  public Mono<Product> updateProduct(final String productId, final ProductCreationData productCreationData) {
    return productDao.findById(productId).flatMap(document -> {
      final ProductDocument updatedDocument = productConverter.updateProductDocument(document, productCreationData);
      return productDao.save(updatedDocument).map(productConverter::createProduct);
    });
  }

  @Override
  public Mono<Product> deleteProduct(final String productId) {
    return getProduct(productId).delayUntil(product -> productDao.deleteById(product.getId()));
  }

}
