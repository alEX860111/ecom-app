package net.brainified.db;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.gridfs.GridFSBucket;
import com.mongodb.reactivestreams.client.gridfs.GridFSBuckets;

@Configuration
class Mongoconfiguration extends AbstractReactiveMongoConfiguration {

  @Bean
  public GridFSBucket gridFSBucket(final MongoClient reactiveMongoClient) throws Exception {
    return GridFSBuckets.create(reactiveMongoClient.getDatabase(getDatabaseName()));
  }

  @Override
  public MongoClient reactiveMongoClient() {
    return MongoClients.create();
  }

  @Override
  protected String getDatabaseName() {
    return "DEFAULT_DB";
  }

}
