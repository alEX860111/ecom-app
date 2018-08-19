package net.brainified.domain.image;

import java.io.InputStream;

import org.springframework.stereotype.Service;

import net.brainified.db.ImageDao;
import net.brainified.db.ImageMetadata;
import reactor.core.publisher.Mono;

@Service
final class ImageServiceImpl implements ImageService {

  private final ImageDao imageDao;

  public ImageServiceImpl(final ImageDao imageDao) {
    this.imageDao = imageDao;
  }

  @Override
  public Mono<String> uploadFromStream(final String filename, final InputStream inputStream, final ImageAttributes imageAttributes) {
    final ImageMetadata imageMetadata = convert(imageAttributes);
    return imageDao.uploadFromStream(filename, inputStream, imageMetadata);
  }

  private ImageMetadata convert(final ImageAttributes imageAttributes) {
    final ImageMetadata imageMetadata = new ImageMetadata();
    imageMetadata.setTitle(imageAttributes.getTitle());
    return imageMetadata;
  }

}
