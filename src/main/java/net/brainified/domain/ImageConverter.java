package net.brainified.domain;

import net.brainified.db.ImageDocument;

interface ImageConverter {

  Image convertImageDocumentToImage(ImageDocument imageDocument);

  ImageDocument convertImageToImageDocument(Image image);

}
