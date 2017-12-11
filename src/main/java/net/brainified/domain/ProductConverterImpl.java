package net.brainified.domain;

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
  public ProductDocument convertProductToProductDocument(final Product product) {
    final ProductDocument productDocument = new ProductDocument();

    productDocument.setName(product.getName());
    productDocument.setPrice(product.getPrice());
    productDocument.setImage(imageConverter.convertImageToImageDocument(product.getImage()));

    return productDocument;
  }

  @Override
  public Product convertProductDocumentToProduct(final ProductDocument productDocument) {
    final Product product = new Product();

    product.setId(productDocument.getId());
    product.setCreatedAt(productDocument.getCreatedAt());

    product.setName(productDocument.getName());
    product.setPrice(productDocument.getPrice());
    product.setImage(imageConverter.convertImageDocumentToImage(productDocument.getImage()));

    return product;
  }

}
