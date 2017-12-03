package net.brainified;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
final class ProductController {

  private ProductService productService;

  public ProductController(final ProductService productService) {
    this.productService = productService;
  }

  @GetMapping("/")
  Flux<Product> home() {
    return productService.getProducts();
  }

}
