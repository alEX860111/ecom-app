package net.brainified;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class Application {

  public static void main(final String[] args) {
    SpringApplication.run(Application.class, args);
  }

}