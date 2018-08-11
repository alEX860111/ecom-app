package net.brainified.domain.product;

import java.time.LocalDateTime;

public final class Product {

  private String id;

  private LocalDateTime createdAt;

  private ProductAttributes attributes;

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

  public ProductAttributes getAttributes() {
    return attributes;
  }

  public void setAttributes(final ProductAttributes attributes) {
    this.attributes = attributes;
  }

}
