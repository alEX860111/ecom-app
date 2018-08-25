package net.brainified.db.product;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
public final class ProductDocument {

  @Id
  private String id;

  @CreatedDate
  private LocalDateTime createdAt;

  private String name;

  private Double price;

  private ObjectId image;

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

  public ObjectId getImage() {
    return image;
  }

  public void setImage(final ObjectId image) {
    this.image = image;
  }

}
