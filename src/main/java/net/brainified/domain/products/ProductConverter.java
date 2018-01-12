package net.brainified.domain.products;

import net.brainified.db.ProductDocument;
import net.brainified.domain.products.Product;

interface ProductConverter {

  ProductDocument updateProductDocument(ProductDocument productDocument, ProductCoreData productCoreData);

  ProductDocument createProductDocument(ProductCoreData productCoreData);

  Product createProduct(ProductDocument productDocument);

}
