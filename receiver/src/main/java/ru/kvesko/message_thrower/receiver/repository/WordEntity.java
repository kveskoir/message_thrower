package ru.kvesko.message_thrower.receiver.repository;


import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "words")
public class WordEntity {
  //UUID is used because long can easily fo over the border
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "word", nullable = false)
  private String word;

  @Column(name = "initialization_time", nullable = false)
  private Instant initializationTime;

  @Column(name = "receive_time", nullable = false)
  private Instant recieveInstant;

  @CreationTimestamp
  @Column(name = "db_creation_time", nullable = false)
  private Instant dbCreationInstant;

  public WordEntity(
      final String word,
      final Instant initializationTime,
      final Instant recieveInstant
  ) {
    this.word = word;
    this.initializationTime = initializationTime;
    this.recieveInstant = recieveInstant;
  }

  public WordEntity() {
  }
}