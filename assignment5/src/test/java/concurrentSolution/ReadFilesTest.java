package concurrentSolution;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReadFilesTest {
  private final static String courseName = "courses.csv";
  private final static String truncatedFileName = "src/test/resources/studentVle.csv";
  private SynContainer container;
  private ReadFiles rf;

  @BeforeEach
  void setUp() {
    container = new SynContainer();
    try {
      File truncatedFile = new File(truncatedFileName);
      rf = new ReadFiles(container,truncatedFile);

    } catch (FileNotFoundException e) {
      System.out.println("File Not Found");
      e.printStackTrace();
    }
  }
  @Test
  void run() throws InterruptedException {
    Thread thread = new Thread(rf);
    thread.start();

    new Thread(() -> {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      while (true) {
        Record r = container.pop();
        if(r == null) {
          break;
        }
      }
    }).start();
  }
}