package net.brainified.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.brainified.db.Product;
import net.brainified.domain.ProductService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
final class ProductController {

  private final ProductService productService;

  public ProductController(final ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public Flux<Product> getProducts() {
    return productService.getProducts();
  }

  @PostMapping
  public Mono<Product> addProduct(@RequestBody final Product product) {
    return productService.addProduct(product);
  }

  @GetMapping("/{productId}")
  public Mono<Product> getProduct(@PathVariable final String productId) {
    return productService.getProduct(productId);
  }

}
