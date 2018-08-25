package net.brainified.db.image;

import java.io.InputStream;

import reactor.core.publisher.Mono;

public interface ImageDao {

  Mono<String> uploadFromStream(String filename, InputStream inputStream, ImageMetadata imageMetadata);

}