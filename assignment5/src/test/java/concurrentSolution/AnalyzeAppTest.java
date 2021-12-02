package concurrentSolution;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AnalyzeAppTest {
  private SynContainer container;
  private int threshold1;
  private int threshold2;
  private final static String truncatedFileName = "src/test/resources/studentVle.csv";


  @Test
  void main1() throws InterruptedException, IOException {
//    Thread producer = new Thread(rf);
//    Thread consumer = new Thread(output1);
//    producer.start();
//    consumer.start();
//    producer.join();
//    consumer.join();
    String str = "src/test/resources/result_con_app\n";
    ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes());
    String[] args = new String[]{"src/test/resources"};
    System.setIn(in);
    AnalyzeApp.main(args);
    File file = new File("src/test/resources/result_con_app/AAA_2013J.csv");
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

  @Test
  void testPath() {
    String path = "src/test/resources/result_con_app/AAA_2013J.csv";
    File file = new File(path);
    System.out.println(file.exists());
  }
}