package net.brainified.domain;

import net.brainified.db.ProductDocument;

interface ProductConverter {

  ProductDocument convertProductToProductDocument(Product product);

  Product convertProductDocumentToProduct(ProductDocument productDocument);

}
