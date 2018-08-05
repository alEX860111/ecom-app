package net.brainified.domain.products;

import net.brainified.db.ProductDocument;

interface ProductConverter {

  ProductDocument updateProductDocument(ProductDocument productDocument, ProductAttributes productAttributes);

  ProductDocument createProductDocument(ProductAttributes productAttributes);

  Product createProduct(ProductDocument productDocument);

}
