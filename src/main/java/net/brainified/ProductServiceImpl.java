package net.brainified;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
final class ProductServiceImpl implements ProductService {

  @Override
  public List<Product> getProducts() {
    final Product iphone = new Product();
    iphone.setName("iphone");
    iphone.setPrice(1199.00d);

    final Product razr = new Product();
    razr.setName("razr");
    razr.setPrice(499.00d);

    return Arrays.asList(iphone, razr);
  }

}
