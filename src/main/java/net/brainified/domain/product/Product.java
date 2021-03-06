package net.brainified.domain.product;

import java.time.LocalDateTime;

public final class Product {

  private String id;

  private LocalDateTime createdAt;

  private String name;

  private Double price;

  private String imageId;

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
  
  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(final Double price) {
    this.price = price;
  }

  public String getImageId() {
    return imageId;
  }

  public void setImageId(final String imageId) {
    this.imageId = imageId;
  }

}
