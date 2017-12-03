package net.brainified.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import net.brainified.db.Product;
import net.brainified.domain.ProductService;
import reactor.core.publisher.Flux;

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

}
