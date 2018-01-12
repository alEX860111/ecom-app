package net.brainified.domain.products;

public class ProductCoreData {

  private String name;

  private Double price;

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

}
