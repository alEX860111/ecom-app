package net.brainified.rest.product;

import org.springframework.stereotype.Service;

import net.brainified.domain.product.Product;
import net.brainified.domain.product.ProductWriteCommand;
import net.brainified.rest.Link;

@Service
final class ProductPayloadConverterImpl implements ProductPayloadConverter {

  @Override
  public ProductWriteCommand convert(final ProductWritePayload productWritePayload) {
    final ProductWriteCommand productWriteCommand = new ProductWriteCommand();
    productWriteCommand.setName(productWritePayload.getName());
    productWriteCommand.setPrice(productWritePayload.getPrice());
    productWriteCommand.setImageId(productWritePayload.getImage().getId());
    return productWriteCommand;
  }

  @Override
  public ProductReadPayload convert(final Product product) {
    final ProductReadPayload productPayload = new ProductReadPayload();
    productPayload.setId(product.getId());
    productPayload.setLink(Link.createSelfLink(""));

    productPayload.setCreatedAt(product.getCreatedAt());
    productPayload.setName(product.getName());
    productPayload.setPrice(product.getPrice());

    final ImageReadReference image = new ImageReadReference();
    image.setId(product.getImageId());
    image.setLink(Link.createSelfLink(""));
    productPayload.setImage(image);

    return productPayload;
  }

}
