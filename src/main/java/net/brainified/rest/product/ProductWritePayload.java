package net.brainified.rest.product;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public final class ProductWritePayload {

  @NotBlank
  private String name;

  @Min(0)
  private Double price;

  @NotNull
  @Valid
  private ImageWriteReference image;

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

  public ImageWriteReference getImage() {
    return image;
  }

  public void setImage(final ImageWriteReference image) {
    this.image = image;
  }

}
