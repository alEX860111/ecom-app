package net.brainified.rest.product;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public final class ProductAttributesPayload {

  @NotNull
  private String name;

  @Min(0)
  private Double price;

  @NotNull
  private ImagePayload image;

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

  public ImagePayload getImage() {
    return image;
  }

  public void setImage(final ImagePayload image) {
    this.image = image;
  }

}
