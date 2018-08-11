package net.brainified.domain.product;

import net.brainified.db.ImageDocument;

interface ImageConverter {

  Image convertImageDocumentToImage(ImageDocument imageDocument);

  ImageDocument convertImageToImageDocument(Image image);

}
