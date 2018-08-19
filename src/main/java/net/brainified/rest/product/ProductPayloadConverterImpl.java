package net.brainified.rest.product;

import org.springframework.stereotype.Service;

import net.brainified.domain.product.Product;
import net.brainified.domain.product.ProductAttributes;

@Service
final class ProductPayloadConverterImpl implements ProductPayloadConverter {

  @Override
  public ProductAttributes convert(final ProductAttributesPayload productAttributesPayload) {
    final ProductAttributes productAttributes = new ProductAttributes();
    productAttributes.setName(productAttributesPayload.getName());
    productAttributes.setPrice(productAttributesPayload.getPrice());
    productAttributes.setImageId(productAttributesPayload.getImageId());
    return productAttributes;
  }

  @Override
  public ProductPayload convert(final Product product) {
    final ProductPayload productPayload = new ProductPayload();
    productPayload.setId(product.getId());
    productPayload.setCreatedAt(product.getCreatedAt());
    productPayload.setAttributes(convert(product.getAttributes()));
    return productPayload;
  }

  private ProductAttributesPayload convert(final ProductAttributes productAttributes) {
    final ProductAttributesPayload productAttributesPayload = new ProductAttributesPayload();
    productAttributesPayload.setName(productAttributes.getName());
    productAttributesPayload.setPrice(productAttributes.getPrice());
    productAttributesPayload.setImageId(productAttributes.getImageId());
    return productAttributesPayload;
  }

}
