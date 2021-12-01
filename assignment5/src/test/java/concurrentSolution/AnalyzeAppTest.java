package concurrentSolution;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AnalyzeAppTest {
  private SynContainer container;
  private int threshold1;
  private int threshold2;
  private String outputDir;
  private Map<String, Map<Integer, Integer>> csv;
  private AggregateOutput output1;
  private AggregateOutput output2;
  private ReadFiles rf;
  private final static String truncatedFileName = "src/test/resources/xaa";

  @BeforeEach
  void setUp() throws FileNotFoundException {
    container = new SynContainer();
    threshold1 = 0;
    threshold2 = 10;
    outputDir = "src/test/resources/result";
    csv = new HashMap<>();
    File truncatedFile = new File(truncatedFileName);
    rf = new ReadFiles(container,truncatedFile);
    output1 = new AggregateOutput(container,outputDir, threshold1);
    output2 = new AggregateOutput(container,outputDir, threshold2);
  }

  @Test
  void main1() throws InterruptedException, IOException {
//    Thread producer = new Thread(rf);
//    Thread consumer = new Thread(output1);
//    producer.start();
//    consumer.start();
//    producer.join();
//    consumer.join();
    String str = "src/test/resources/result\n";
    ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes());
    String[] args = new String[]{"src/test/resources"};
    System.setIn(in);
    AnalyzeApp.main(args);

    Thread.sleep(2000);
    File file = new File(outputDir, "AAA_2013J.csv");
    Assertions.assertTrue(file.exists());
  }
//  @Test
//  void main2() throws InterruptedException {
//    Thread producer = new Thread(rf);
//    Thread consumer = new Thread(output2);
//    producer.start();
//    consumer.start();
//    producer.join();
//    consumer.join();
//    Thread.sleep(2000);
//    File file = new File(outputDir, "activity-10.csv");
//    Assertions.assertTrue(file.exists());
//
//  }
}