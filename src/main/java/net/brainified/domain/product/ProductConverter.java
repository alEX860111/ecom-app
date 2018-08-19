package net.brainified.domain.product;

import net.brainified.db.product.ProductDocument;

interface ProductConverter {

  ProductDocument updateProductDocument(ProductDocument productDocument, ProductAttributes productAttributes);

  ProductDocument createProductDocument(ProductAttributes productAttributes);

  Product createProduct(ProductDocument productDocument);

}
