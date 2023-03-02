package ru.kvesko.message_thrower.sender.web;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kvesko.message_thrower.sender.service.SpammingService;

@RestController
@AllArgsConstructor
@RequestMapping("/spamming")
public class SenderController {

  private final SpammingService messageSpammer;

  @PostMapping("/start")
  public void startSpamming() {
    messageSpammer.start();
  }

  @PostMapping("/stop")
  public void stopSpamming() {
    messageSpammer.stop();
  }

  @GetMapping("/get-best-rps")
  public ResponseEntity<Long> getBestRps() {
    return ResponseEntity.ok(messageSpammer.getBestRps());
  }

}
