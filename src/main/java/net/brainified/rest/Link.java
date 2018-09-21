package net.brainified.rest;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonIgnore;

public final class Link {

  private final URI uri;

  private final String href;

  private final String rel;

  public Link(final URI uri, final String rel) {
    this.uri = uri;
    this.href = uri.toString();
    this.rel = rel;
  }

  @JsonIgnore
  public URI getUri() {
    return uri;
  }

  public String getHref() {
    return href;
  }

  public String getRel() {
    return rel;
  }

  public static Link createSelfLink(final URI uri) {
    return new Link(uri, "self");
  }

}
