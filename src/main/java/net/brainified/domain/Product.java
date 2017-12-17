package net.brainified.domain;

import java.time.LocalDateTime;

public final class Product extends ProductCoreData {

  private String id;

  private LocalDateTime createdAt;

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

}
