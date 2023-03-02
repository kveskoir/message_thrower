package ru.kvesko.messages;

import java.time.Instant;

public record WordSpam(String word, Instant creationTime) {}
