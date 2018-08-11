package net.brainified.domain.product;

import org.springframework.stereotype.Service;

import net.brainified.db.ImageDocument;

@Service
final class ImageConverterImpl implements ImageConverter {

  @Override
  public Image convertImageDocumentToImage(final ImageDocument imageDocument) {
    final Image image = new Image();
    image.setTitle(imageDocument.getTitle());
    image.setPath(imageDocument.getPath());
    return image;
  }

  @Override
  public ImageDocument convertImageToImageDocument(final Image image) {
    final ImageDocument imageDocument = new ImageDocument();
    imageDocument.setTitle(image.getTitle());
    imageDocument.setPath(image.getPath());
    return imageDocument;
  }

}
