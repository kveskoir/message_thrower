package ru.kvesko.message_thrower.receiver.web;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kvesko.message_thrower.receiver.repository.WordRepository;

@RestController
@AllArgsConstructor
@RequestMapping("/metrics")
public class ReceiverMetricsController {

  private final WordRepository wordRepository;

  @GetMapping("/get-best-rps-kafka")
  public ResponseEntity<Long> getBestRpsKafka() {
    return ResponseEntity.ok(wordRepository.getBestRpsKafka());
  }

  @GetMapping("/get-best-rps-db")
  public ResponseEntity<Long> getBestRpsDb() {
    return ResponseEntity.ok(wordRepository.getBestRpsDb());
  }

}
