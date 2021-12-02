package concurrentSolution;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SynContainerTest {

  private Record record1 = new Record("AAA_2013J", 6, 10);
  private Record record2 = new Record("CCC_2013J", -6, 9);
  private Record record3 = new Record("CCC_2013J", -6, 2);
  private static final int BUF_SIZE = 2000;
  private boolean end = true;
  private SynContainer container1;
  private SynContainer container2;
  private SynContainer container3;
  @BeforeEach
  void setUp() {
//    this.records  = new Record[]{record1};
    this.container1 = new SynContainer();
    this.container2 = new SynContainer();
    this.container3 = new SynContainer();
    container3.add(record3);
  }

  @Test
  void add() {
    container1.add(record1);
    assertTrue(container1.getRecords()[0].equals(record1) );
  }

  @Test
  void pop() {
    container2.add(record2);
    Record r2 = container2.pop();
    assertEquals(r2,record2);

  }

  @Test
  void isEnd() {
    Boolean end = container1.isEnd();
    Assertions.assertTrue(!end);
  }

  @Test
  void setEnd() {
    container2.setEnd(end);
    Assertions.assertTrue(end);
  }

  @Test
  void getRecords() {
    assertEquals(container3.getRecords()[0],record3);
  }
}