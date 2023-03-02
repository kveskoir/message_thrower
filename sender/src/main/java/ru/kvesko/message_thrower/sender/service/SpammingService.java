package ru.kvesko.message_thrower.sender.service;

import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class SpammingService {

  private Thread worker;
  private final Runnable spammingRunnable;

  private final LoadingCache<String, Long> rpsCache;

  public SpammingService(
      @Qualifier("spammingRunnable") final Runnable spammingRunnable,
      @Qualifier("rpsCache") final LoadingCache<String, Long> rpsCache
  ) {
    this.spammingRunnable = spammingRunnable;
    this.worker = new Thread(spammingRunnable);
    this.rpsCache = rpsCache;
  }

  public void start() {
    synchronized(this) {
      if (!worker.isAlive()) {
        worker = new Thread(spammingRunnable);
      }
    }
    worker.start();
  }

  public void stop() {
    worker.interrupt();
  }

  public long getBestRps() {
    try {
      return rpsCache.get("test");
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
  }

}
