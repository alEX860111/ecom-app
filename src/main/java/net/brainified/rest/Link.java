package net.brainified.rest;

public final class Link {

  private final String href;

  private final String rel;

  public Link(final String href, final String rel) {
    this.href = href;
    this.rel = rel;
  }

  public String getHref() {
    return href;
  }

  public String getRel() {
    return rel;
  }
  
  public static Link createSelfLink(final String href) {
    return new Link(href, "self");
  }

}
