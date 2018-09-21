package net.brainified.rest.product;

import java.net.URI;

import javax.validation.Valid;

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

import net.brainified.domain.product.ProductService;
import net.brainified.domain.product.ProductCreationData;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
final class ProductController {

  private final ProductService productService;

  private final ProductPayloadConverter productPayloadConverter;

  public ProductController(final ProductService productService, final ProductPayloadConverter productPayloadConverter) {
    this.productService = productService;
    this.productPayloadConverter = productPayloadConverter;
  }

  @PostMapping
  public Mono<ResponseEntity<ProductReadPayload>> addProduct(
      @RequestBody @Valid final ProductWritePayload productWritePayload,
      final UriComponentsBuilder uriComponentBuilder) {

    final ProductCreationData productWriteCommand = productPayloadConverter.convert(productWritePayload);
    return productService.addProduct(productWriteCommand)
        .map(productPayloadConverter::convert)
        .map(productPayload -> {
          final URI location = uriComponentBuilder
              .path("/products/")
              .path(productPayload.getId())
              .build()
              .toUri();
          return ResponseEntity.created(location).body(productPayload);
        });
  }

  @GetMapping
  public Flux<ProductReadPayload> getProducts(
      @RequestParam(name = "page-index", defaultValue = "0") final int pageIndex,
      @RequestParam(name = "page-size", defaultValue = "10") final int pageSize,
      @RequestParam(name = "sort-direction", defaultValue = "desc") final String sortDirection,
      @RequestParam(name = "sort-property", defaultValue = "createdAt") final String sortProperty) {

    final Sort sort = Sort.by(Direction.fromString(sortDirection), sortProperty);
    return productService.getProducts(PageRequest.of(pageIndex, pageSize, sort))
        .map(productPayloadConverter::convert);
  }

  @GetMapping("/{productId}")
  public Mono<ResponseEntity<ProductReadPayload>> getProduct(
      @PathVariable final String productId) {

    return productService.getProduct(productId)
        .map(productPayloadConverter::convert)
        .map(productPayload -> ResponseEntity.ok(productPayload))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PutMapping("/{productId}")
  public Mono<ResponseEntity<ProductReadPayload>> updateProduct(
      @RequestBody @Valid final ProductWritePayload productWritePayload,
      @PathVariable final String productId) {

    final ProductCreationData productWriteCommand = productPayloadConverter.convert(productWritePayload);
    return productService.updateProduct(productId, productWriteCommand)
        .map(productPayloadConverter::convert)
        .map(productPayload -> ResponseEntity.ok(productPayload))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{productId}")
  public Mono<ResponseEntity<ProductReadPayload>> deleteProduct(
      @PathVariable final String productId) {

    return productService.deleteProduct(productId)
        .map(productPayloadConverter::convert)
        .map(productPayload -> ResponseEntity.ok(productPayload))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

}
