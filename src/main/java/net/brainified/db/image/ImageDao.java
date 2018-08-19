package net.brainified.db.image;

import java.io.InputStream;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import com.mongodb.reactivestreams.client.gridfs.AsyncInputStream;
import com.mongodb.reactivestreams.client.gridfs.GridFSBucket;
import com.mongodb.reactivestreams.client.gridfs.helpers.AsyncStreamHelper;

import reactor.core.publisher.Mono;

@Service
public final class ImageDao {

  private final GridFSBucket gridFsBucket;

  public ImageDao(final GridFSBucket gridFsBucket) {
    this.gridFsBucket = gridFsBucket;
  }

  public Mono<String> uploadFromStream(final String filename, final InputStream inputStream, final ImageMetadata imageMetadata) {
    final AsyncInputStream asyncInputStream = AsyncStreamHelper.toAsyncInputStream(inputStream);

    final GridFSUploadOptions gridFSUploadOptions = createUploadOptions(imageMetadata);

    return Mono.from(gridFsBucket.uploadFromStream(filename, asyncInputStream, gridFSUploadOptions))
        .map(ObjectId::toHexString);
  }

  private GridFSUploadOptions createUploadOptions(final ImageMetadata imageMetadata) {
    final GridFSUploadOptions gridFSUploadOptions = new GridFSUploadOptions();

    final Document document = new Document("title", imageMetadata.getTitle());
    gridFSUploadOptions.metadata(document);

    return gridFSUploadOptions;
  }

}
