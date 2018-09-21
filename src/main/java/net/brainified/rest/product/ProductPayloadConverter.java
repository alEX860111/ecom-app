package net.brainified.rest.product;

import org.springframework.web.util.UriComponentsBuilder;

import net.brainified.domain.product.Product;
import net.brainified.domain.product.ProductCreationData;

interface ProductPayloadConverter {

  ProductCreationData convert(ProductCreationDataPayload productCreationDataPayload);

  ProductPayload convert(Product product, UriComponentsBuilder uriComponentBuilder);

}