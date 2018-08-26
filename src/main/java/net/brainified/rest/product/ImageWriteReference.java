package net.brainified.rest.product;

import javax.validation.constraints.NotBlank;

public final class ImageWriteReference {

  @NotBlank
  private String id;

  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }

}
