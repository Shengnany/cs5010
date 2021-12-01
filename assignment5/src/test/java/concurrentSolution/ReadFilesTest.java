package concurrentSolution;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReadFilesTest {
  private final static String courseName = "courses.csv";
  private final static String truncatedFileName = "src/test/resources/xaa";
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
  void run() {
    rf.run();
    Assertions.assertTrue(container.getRecords()[0].getCount() == 3);
  }
}