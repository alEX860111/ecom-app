package net.brainified.domain.product;

public final class ProductAttributes {

  private String name;

  private Double price;

  private String imageId;

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
