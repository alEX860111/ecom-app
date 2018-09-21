package net.brainified.rest.product;

import org.springframework.stereotype.Service;

import net.brainified.domain.product.Product;
import net.brainified.domain.product.ProductCreationData;
import net.brainified.rest.Link;

@Service
final class ProductPayloadConverterImpl implements ProductPayloadConverter {

  @Override
  public ProductCreationData convert(final ProductCreationDataPayload payload) {
    final ProductCreationData productCreationData = new ProductCreationData();
    productCreationData.setName(payload.getName());
    productCreationData.setPrice(payload.getPrice());
    productCreationData.setImageId(payload.getImage().getId());
    return productCreationData;
  }

  @Override
  public ProductPayload convert(final Product product) {
    final ProductPayload productPayload = new ProductPayload();
    productPayload.setId(product.getId());
    productPayload.setLink(Link.createSelfLink(""));

    productPayload.setCreatedAt(product.getCreatedAt());
    productPayload.setName(product.getName());
    productPayload.setPrice(product.getPrice());

    final ProductPayload.Image image = new ProductPayload.Image();
    image.setId(product.getImageId());
    image.setLink(Link.createSelfLink(""));
    productPayload.setImage(image);

    return productPayload;
  }

}
