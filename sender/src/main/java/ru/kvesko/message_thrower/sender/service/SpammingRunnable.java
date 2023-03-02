package ru.kvesko.message_thrower.sender.service;

import com.google.common.cache.LoadingCache;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import ru.kvesko.messages.WordSpam;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
public class SpammingRunnable implements Runnable {
  private static final String TOPIC_NAME = "test";
  private final KafkaTemplate<String, WordSpam> kafkaTemplate;
  private final LoadingCache<String, Long> cache;
  @Value(value = "${word-generation.timeout-ms}")
  private int timeoutMs;

  private long messagesCount = 0;
  private long timeInMillis = System.currentTimeMillis();

  public SpammingRunnable(
      final KafkaTemplate<String, WordSpam> kafkaTemplate,
      @Qualifier("rpsCache") final LoadingCache<String, Long> cache
  ) {
    this.kafkaTemplate = kafkaTemplate;
    this.cache = cache;
  }

  @SneakyThrows
  public void run() {
    while (true) {
      final String msg = RandomStringUtils.randomAlphabetic(getRandomNumber(1, 100));
      CompletableFuture<SendResult<String, WordSpam>> responseFuture = kafkaTemplate
          .send(TOPIC_NAME, new WordSpam(msg, Instant.now()));
      responseFuture.thenAccept(r -> {
        try {
          messagesCount++;
          long delta = System.currentTimeMillis() - timeInMillis;
          if (delta <= 1000) {
            if (messagesCount > cache.get(TOPIC_NAME)) {
              cache.put(TOPIC_NAME, messagesCount);
              messagesCount = 0;
            }
          } else {
            messagesCount = 0;
            timeInMillis = System.currentTimeMillis();
          }
        } catch (ExecutionException e) {
          //skip
        }
      });

//      log.info(Thread.currentThread().getName() + msg);
      Thread.sleep(timeoutMs);
    }
  }

  public long getBestWordsPerMinuteValue() {
    try {
      return cache.get(TOPIC_NAME);
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
  }

  private int getRandomNumber(int min, int max) {
    return (int) ((Math.random() * (max - min)) + min);
  }

}
