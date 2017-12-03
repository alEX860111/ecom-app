package net.brainified.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import net.brainified.db.Product;
import net.brainified.domain.ProductService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
final class ProductController {

  private final ProductService productService;

  public ProductController(final ProductService productService) {
    this.productService = productService;
  }

  @GetMapping("/products")
  public Flux<Product> getProducts() {
    return productService.getProducts();
  }

  @PostMapping("/products")
  public Mono<Product> addProduct(final Product product) {
    return productService.addProduct(product);
  }

  @GetMapping("/products/{productId}")
  public Mono<Product> getProduct(@PathVariable String productId) {
    return productService.getProduct(productId);
  }

}
