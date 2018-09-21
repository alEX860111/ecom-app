package net.brainified.domain.product;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import net.brainified.db.product.ProductDocument;

@Service
final class ProductConverterImpl implements ProductConverter {

  @Override
  public ProductDocument updateProductDocument(
      final ProductDocument productDocument,
      final ProductCreationData productCreationData) {
    productDocument.setName(productCreationData.getName());
    productDocument.setPrice(productCreationData.getPrice());
    productDocument.setImage(new ObjectId(productCreationData.getImageId()));
    return productDocument;
  }

  @Override
  public ProductDocument createProductDocument(final ProductCreationData productCreationData) {
    return updateProductDocument(new ProductDocument(), productCreationData);
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
