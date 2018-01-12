package net.brainified.rest;

import java.net.URI;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import net.brainified.domain.products.Product;
import net.brainified.domain.products.ProductCoreData;
import net.brainified.domain.products.ProductService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
final class ProductController {

  private final ProductService productService;

  public ProductController(final ProductService productService) {
    this.productService = productService;
  }

  @PostMapping
  public Mono<ResponseEntity<Product>> addProduct(@RequestBody final ProductCoreData productCoreData,
      final UriComponentsBuilder uriComponentBuilder) {
    return productService.addProduct(productCoreData).map(savedProduct -> {
      final URI location = URI.create(uriComponentBuilder.path("/").path(savedProduct.getId()).toUriString());
      return ResponseEntity.created(location).body(savedProduct);
    });
  }

  @GetMapping
  public Flux<Product> getProducts(@RequestParam(name = "page-index", defaultValue = "0") final int pageIndex,
      @RequestParam(name = "page-size", defaultValue = "10") final int pageSize,
      @RequestParam(name = "sort-direction", defaultValue = "desc") final String sortDirection,
      @RequestParam(name = "sort-property", defaultValue="createdAt") final String sortProperty) {
    final Sort sort = Sort.by(Direction.fromString(sortDirection), sortProperty);
    return productService.getProducts(PageRequest.of(pageIndex, pageSize, sort));
  }

  @GetMapping("/{productId}")
  public Mono<ResponseEntity<Product>> getProduct(@PathVariable final String productId) {
    return productService.getProduct(productId).map(product -> ResponseEntity.ok(product))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PutMapping("/{productId}")
  public Mono<ResponseEntity<Product>> updateProduct(@RequestBody final ProductCoreData productCoreData,
      @PathVariable final String productId) {
    return productService.updateProduct(productId, productCoreData)
        .map(updatedProduct -> ResponseEntity.ok(updatedProduct)).defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{productId}")
  public Mono<ResponseEntity<Product>> deleteProduct(@PathVariable final String productId) {
    return productService.deleteProduct(productId).map(deletedProduct -> ResponseEntity.ok(deletedProduct))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

}
