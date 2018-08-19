package net.brainified.rest.image;

import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.FormFieldPart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.brainified.domain.image.ImageAttributes;
import net.brainified.domain.image.ImageService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/images")
final class ImageController {

  private final ImageService imageService;

  public ImageController(final ImageService imageService) {
    this.imageService = imageService;
  }

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public Mono<String> addImage(@RequestBody final Flux<Part> parts) {
    System.out.println("post /images");
    return parts
        .collectMap(Part::name)
        .flatMap(formData -> {
          final FilePart filePart = (FilePart) formData.get("file");
          final FormFieldPart formFieldPart = (FormFieldPart) formData.get("title");

          return DataBufferUtils.join(filePart.content()).flatMap(buffer -> {
            final ImageAttributes imageAttributes = new ImageAttributes();
            imageAttributes.setTitle(formFieldPart.value());
            return imageService.uploadFromStream(filePart.filename(), buffer.asInputStream(), imageAttributes);
          });
        });
  }

}
