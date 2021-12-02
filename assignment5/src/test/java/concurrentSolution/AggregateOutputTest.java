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
  private final static String truncatedFileName = "src/test/resources/xaa";

  @BeforeEach
  void setUp() throws FileNotFoundException {
    container = new SynContainer();
    threshold1 = 0;
    threshold2 = 10;
    outputDir = "src/test/resources/result";
    csv = new HashMap<>();
    File truncatedFile = new File(truncatedFileName);
    ReadFiles rf = new ReadFiles(container,truncatedFile);
    rf.run();
  }
//  public void run(){
//    while(true){
//      Record r = container.pop();
//      if(r == null) {
//        new Thread(new WriteOutput(csv,outputDir,threshold)).start();
//        break;
//      }
//      Map<Integer, Integer> course = csv.getOrDefault(r.getKey(), new HashMap<>());
//      course.put(r.getDate(),course.getOrDefault(r.getDate(),0) + r.getCount());
//      csv.put(r.getKey(),course);
//    }
//    return;
//  }
  @Test
  void run1() {
    output1 = new AggregateOutput(container,outputDir, threshold1);
    output1.run();
    File file = new File(outputDir, "AAA_2013J.csv");
    Assertions.assertTrue(file.exists());
  }


}