package ru.kvesko.message_thrower.receiver.listeners;

import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.kvesko.message_thrower.receiver.repository.WordEntity;
import ru.kvesko.message_thrower.receiver.repository.WordRepository;
import ru.kvesko.messages.WordSpam;

import java.time.Instant;

@Service
@AllArgsConstructor
public class SpamListener {
  private final WordRepository wordRepository;
  @KafkaListener(topics = "test", groupId = "group_id")
  public void listenGroupFoo(WordSpam message) {
    System.out.println("Received Message in group foo: " + message);
    wordRepository.save(new WordEntity(message.word(), message.creationTime(), Instant.now()));
  }

}
