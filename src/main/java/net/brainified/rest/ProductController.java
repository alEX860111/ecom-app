package net.brainified.rest;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

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
  public Mono<ResponseEntity<Product>> addProduct(@RequestBody final Product product,
      final UriComponentsBuilder uriComponentBuilder) {
    return productService.addProduct(product).map(savedProduct -> {
      final URI location = URI.create(uriComponentBuilder.path("/").path(savedProduct.getId()).toUriString());
      return ResponseEntity.created(location).body(savedProduct);
    });
  }

  @GetMapping("/{productId}")
  public Mono<ResponseEntity<Product>> getProduct(@PathVariable final String productId) {
    return productService.getProduct(productId).map(product -> ResponseEntity.ok(product))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

}
