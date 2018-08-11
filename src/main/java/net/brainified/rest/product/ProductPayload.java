package net.brainified.rest.product;

import java.time.LocalDateTime;

public final class ProductPayload {

  private String id;

  private LocalDateTime createdAt;

  private ProductAttributesPayload attributes;

  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(final LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public ProductAttributesPayload getAttributes() {
    return attributes;
  }

  public void setAttributes(final ProductAttributesPayload attributes) {
    this.attributes = attributes;
  }

}
