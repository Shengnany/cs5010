package concurrentSolution;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AggregateOutputTest {
  private SynContainer container;
  private int threshold1;
  private int threshold2;
  private String outputDir;
  private Map<String, Map<Integer, Integer>> csv;
  private AggregateOutput output1;
  private AggregateOutput output2;
  private final static String truncatedFileName = "src/test/resources/studentVle.csv";

  @BeforeEach
  void setUp() throws FileNotFoundException {
    container = new SynContainer();
    threshold1 = 0;
    threshold2 = 10;
    outputDir = "src/test/resources/result";
    File outputDirFile = new File(outputDir);
    outputDirFile.mkdirs();
    csv = new HashMap<>();
    File truncatedFile = new File(truncatedFileName);
    ReadFiles rf = new ReadFiles(container,truncatedFile);
    Thread thread = new Thread(rf);
    thread.start();
  }

  @Test
  void run1() throws InterruptedException {
    output1 = new AggregateOutput(container,outputDir, threshold1);
    Thread consumer = new Thread(output1);
    consumer.start();
    consumer.join();
    File file = new File(outputDir + "/AAA_2013J.csv");
    Assertions.assertTrue(file.exists());
  }


}