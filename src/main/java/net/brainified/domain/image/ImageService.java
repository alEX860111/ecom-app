package net.brainified.domain.image;

import java.io.InputStream;

import reactor.core.publisher.Mono;

public interface ImageService {

  Mono<String> uploadFromStream(String filename, InputStream inputStream, ImageAttributes imageAttributes);

}