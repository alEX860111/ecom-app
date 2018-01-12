package net.brainified.domain.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.brainified.db.ProductDocument;
import net.brainified.domain.products.Product;

@Service
final class ProductConverterImpl implements ProductConverter {

  private final ImageConverter imageConverter;

  @Autowired
  public ProductConverterImpl(final ImageConverter imageConverter) {
    this.imageConverter = imageConverter;
  }

  @Override
  public ProductDocument updateProductDocument(final ProductDocument productDocument,
      final ProductCoreData productCoreData) {
    productDocument.setName(productCoreData.getName());
    productDocument.setPrice(productCoreData.getPrice());
    productDocument.setImage(imageConverter.convertImageToImageDocument(productCoreData.getImage()));
    return productDocument;
  }

  @Override
  public ProductDocument createProductDocument(final ProductCoreData productCoreData) {
    return updateProductDocument(new ProductDocument(), productCoreData);
  }

  @Override
  public Product createProduct(final ProductDocument productDocument) {
    final Product product = new Product();

    product.setId(productDocument.getId());
    product.setCreatedAt(productDocument.getCreatedAt());

    product.setName(productDocument.getName());
    product.setPrice(productDocument.getPrice());
    product.setImage(imageConverter.convertImageDocumentToImage(productDocument.getImage()));

    return product;
  }

}
