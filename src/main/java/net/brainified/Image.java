package net.brainified;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
final class Image {

  private String title;

  private String path;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

}
