package net.brainified.domain.product;

import net.brainified.db.product.ProductDocument;

interface ProductConverter {

  ProductDocument updateProductDocument(ProductDocument productDocument, ProductCreationData productCreationData);

  ProductDocument createProductDocument(ProductCreationData productCreationData);

  Product createProduct(ProductDocument productDocument);

}
