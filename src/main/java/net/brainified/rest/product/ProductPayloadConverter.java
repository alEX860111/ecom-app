package net.brainified.rest.product;

import net.brainified.domain.product.Product;
import net.brainified.domain.product.ProductWriteCommand;

interface ProductPayloadConverter {

  ProductWriteCommand convert(ProductWritePayload productWritePayload);

  ProductReadPayload convert(Product product);

}