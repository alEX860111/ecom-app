package net.brainified.rest.product;

import net.brainified.domain.product.Product;
import net.brainified.domain.product.ProductCreationData;

interface ProductPayloadConverter {

  ProductCreationData convert(ProductWritePayload productWritePayload);

  ProductReadPayload convert(Product product);

}