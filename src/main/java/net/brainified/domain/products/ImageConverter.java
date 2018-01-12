package net.brainified.domain.products;

import net.brainified.db.ImageDocument;

interface ImageConverter {

  Image convertImageDocumentToImage(ImageDocument imageDocument);

  ImageDocument convertImageToImageDocument(Image image);

}
