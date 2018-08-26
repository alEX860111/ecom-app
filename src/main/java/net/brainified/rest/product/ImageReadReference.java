package net.brainified.rest.product;

import net.brainified.rest.Link;

public final class ImageReadReference {

  private String id;

  private Link link;

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

}
