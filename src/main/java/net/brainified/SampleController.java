package net.brainified;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

  private ProductService productService;

  public SampleController(final ProductService productService) {
    this.productService = productService;
  }

  @GetMapping("/")
  List<Product> home() {
    return productService.getProducts();
  }

}
