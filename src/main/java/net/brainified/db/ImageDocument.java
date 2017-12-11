package net.brainified.db;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public final class ImageDocument {

  private String title;

  private String path;

  public String getTitle() {
    return title;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  public String getPath() {
    return path;
  }

  public void setPath(final String path) {
    this.path = path;
  }

}
