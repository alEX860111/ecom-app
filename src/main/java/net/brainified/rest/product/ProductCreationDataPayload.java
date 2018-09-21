package net.brainified.rest.product;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public final class ProductCreationDataPayload {

  @NotBlank
  private String name;

  @Min(0)
  private Double price;

  @NotNull
  @Valid
  private Image image;

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

  public Image getImage() {
    return image;
  }

  public void setImage(final Image image) {
    this.image = image;
  }

  public static final class Image {

    @NotBlank
    private String id;

    public String getId() {
      return id;
    }

    public void setId(final String id) {
      this.id = id;
    }

  }

}
