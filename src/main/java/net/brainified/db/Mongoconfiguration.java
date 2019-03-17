package net.brainified.db;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoDatabase;
import com.mongodb.reactivestreams.client.gridfs.GridFSBucket;
import com.mongodb.reactivestreams.client.gridfs.GridFSBuckets;

@Configuration
class MongoConfiguration {

  @Bean
  public GridFSBucket gridFSBucket(final MongoProperties properties, final MongoClient client) throws Exception {
    final String databaseName = properties.getDatabase();
    final MongoDatabase database = client.getDatabase(databaseName);

    return GridFSBuckets.create(database);
  }

}
