package concurrentSolution;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RecordTest {
  private String key = "AAA_2013J";
  private int date = 6;
  private int count = 10;
  private Record record;

  @BeforeEach
  void setUp() {
    record = new Record(key,date,count);
  }

  @Test
  void getKey() {
    Assertions.assertEquals(record.getKey(), this.key);
  }

  @Test
  void getDate() {
    Assertions.assertEquals(record.getDate(), this.date);
  }

  @Test
  void getCount() {
    Assertions.assertEquals(record.getCount(), this.count);
  }
}