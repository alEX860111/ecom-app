package net.brainified.reactive;

import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;

class ReactiveTest {

  @Test
  void test() throws InterruptedException {
    final CountDownLatch latch = new CountDownLatch(1);

    final Flux<Integer> flux = Flux.just(1, 2, 3);

    flux.log().subscribe();

    flux.doOnNext(number -> {
      System.out.println(number);
    }).subscribe();

    flux.doOnComplete(() -> {
      System.out.println("completed");
      latch.countDown();
    }).subscribe();
    System.out.println("pre latch");
    latch.await();
    System.out.println("test end");
  }

}
