package net.brainified.domain.product;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import net.brainified.db.product.ProductDocument;

@Service
final class ProductConverterImpl implements ProductConverter {

  @Override
  public ProductDocument updateProductDocument(
      final ProductDocument productDocument,
      final ProductWriteCommand productWriteCommand) {
    productDocument.setName(productWriteCommand.getName());
    productDocument.setPrice(productWriteCommand.getPrice());
    productDocument.setImage(new ObjectId(productWriteCommand.getImageId()));
    return productDocument;
  }

  @Override
  public ProductDocument createProductDocument(final ProductWriteCommand productWriteCommand) {
    return updateProductDocument(new ProductDocument(), productWriteCommand);
  }

  @Override
  public Product createProduct(final ProductDocument productDocument) {
    final Product product = new Product();
    product.setId(productDocument.getId());
    product.setCreatedAt(productDocument.getCreatedAt());
    product.setName(productDocument.getName());
    product.setPrice(productDocument.getPrice());
    product.setImageId((productDocument.getImage().toHexString()));
    return product;
  }

}
