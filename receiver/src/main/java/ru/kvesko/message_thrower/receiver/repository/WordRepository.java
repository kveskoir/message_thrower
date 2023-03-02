package ru.kvesko.message_thrower.receiver.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends CrudRepository<WordEntity, Long> {

  @Query(
      value = "SELECT count(*) as occ\n" +
          "FROM words\n" +
          "GROUP BY date_trunc('second', receive_time)\n" +
          "ORDER BY occ DESC\n" +
          "LIMIT 1",
      nativeQuery = true
  )
  Long getBestRpsKafka();

  @Query(
      value = "SELECT count(*) as occ\n" +
          "FROM words\n" +
          "GROUP BY date_trunc('second', db_creation_time)\n" +
          "ORDER BY occ DESC\n" +
          "LIMIT 1",
      nativeQuery = true)
  Long getBestRpsDb();

}