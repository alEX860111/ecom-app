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

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/images")
final class ImageController {

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public Flux<String> addImage(@RequestBody final Flux<Part> parts) {
    System.out.println("post /images");
    return parts

        .doOnNext(part -> {
          System.out.println(part.name());
          if (part instanceof FilePart) {
            final FilePart filePart = (FilePart) part;
            System.out.println(filePart.filename());

            // part.content().doOnNext(buffer -> {
            // System.out.println(buffer.toString());
            // }).subscribe();

            DataBufferUtils.join(part.content()).doOnNext(buffer -> {
              System.out.println(buffer.readableByteCount());
            }).subscribe();
          }
          if (part instanceof FormFieldPart) {
            final FormFieldPart formFieldPart = (FormFieldPart) part;
            System.out.println(formFieldPart.value());
          }
        })
        .map(part -> part.name());

  }

}
