package net.brainified.domain.product;

import net.brainified.db.product.ProductDocument;

interface ProductConverter {

  ProductDocument updateProductDocument(ProductDocument productDocument, ProductWriteCommand productWriteCommand);

  ProductDocument createProductDocument(ProductWriteCommand productWriteCommand);

  Product createProduct(ProductDocument productDocument);

}
