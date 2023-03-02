package ru.kvesko.message_thrower.sender.configurations;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import ru.kvesko.message_thrower.sender.service.SpammingRunnable;
import ru.kvesko.messages.WordSpam;

@Configuration
public class SpammerServiceConfiguration {

  @Bean
  @Qualifier("spammingRunnable")
  public Runnable spammingRunnable(final KafkaTemplate<String, WordSpam> kafkaTemplate) {
    return new SpammingRunnable(kafkaTemplate, rspCache());
  }

  @Bean
  @Qualifier("rpsCache")
  public LoadingCache<String, Long> rspCache() {
    CacheLoader<String, Long> loader = new CacheLoader<>() {
      @Override
      public Long load(String key) {
        return 0L;
      }
    };

    return CacheBuilder.newBuilder().build(loader);
  }


}
