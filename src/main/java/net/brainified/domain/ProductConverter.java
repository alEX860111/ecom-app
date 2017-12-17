package net.brainified.domain;

import net.brainified.db.ProductDocument;

interface ProductConverter {

  ProductDocument convertProductToProductDocument(ProductCoreData productCoreData);

  Product convertProductDocumentToProduct(ProductDocument productDocument);

}
