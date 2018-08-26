package net.brainified.rest.product;

import java.time.LocalDateTime;

import net.brainified.rest.Link;

public final class ProductReadPayload {

  private String id;

  private Link link;

  private LocalDateTime createdAt;

  private String name;

  private Double price;

  private ImageReadReference image;

  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public Link getLink() {
    return link;
  }

  public void setLink(final Link link) {
    this.link = link;
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

  public ImageReadReference getImage() {
    return image;
  }

  public void setImage(final ImageReadReference image) {
    this.image = image;
  }

}
