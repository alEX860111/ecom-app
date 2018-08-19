package net.brainified.domain.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.brainified.db.ProductDocument;

@Service
final class ProductConverterImpl implements ProductConverter {

  private final ImageConverter imageConverter;

  @Autowired
  public ProductConverterImpl(final ImageConverter imageConverter) {
    this.imageConverter = imageConverter;
  }

  @Override
  public ProductDocument updateProductDocument(
      final ProductDocument productDocument,
      final ProductAttributes productAttributes) {
    productDocument.setName(productAttributes.getName());
    productDocument.setPrice(productAttributes.getPrice());
    productDocument.setImage(imageConverter.convertImageToImageDocument(productAttributes.getImage()));
    return productDocument;
  }

  @Override
  public ProductDocument createProductDocument(final ProductAttributes productAttributes) {
    return updateProductDocument(new ProductDocument(), productAttributes);
  }

  @Override
  public Product createProduct(final ProductDocument productDocument) {
    final Product product = new Product();

    product.setId(productDocument.getId());
    product.setCreatedAt(productDocument.getCreatedAt());

    final ProductAttributes productAttributes = new ProductAttributes();
    productAttributes.setName(productDocument.getName());
    productAttributes.setPrice(productDocument.getPrice());
    productAttributes.setImage(imageConverter.convertImageDocumentToImage(productDocument.getImage()));

    product.setAttributes(productAttributes);

    return product;
  }

}