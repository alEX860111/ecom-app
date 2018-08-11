package net.brainified.rest.product;

import net.brainified.domain.product.Product;
import net.brainified.domain.product.ProductAttributes;

interface ProductPayloadConverter {

  ProductAttributes convert(ProductAttributesPayload productAttributesPayload);

  ProductPayload convert(Product product);

}