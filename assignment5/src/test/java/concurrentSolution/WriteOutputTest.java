package concurrentSolution;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriteOutputTest {
  private int threshold0;
  private int threshold1;
  private String[] HEADER = {"Date", "Count"};
  private String[] HEADER_TD = {"Course", "Date", "Count"};
  private  Map<String, Map<Integer, Integer>> csv ;
  private String outputDir;
  private File dir;
  private static String SUFFIX = ".csv";
  private static String PREFIX = "activity-";
  private static int COLUMN = 2;
  private static int COLUMN_THRESHOLD = 3;
  private WriteOutput w1;
  private WriteOutput w2;


  @BeforeEach
  void setUp() {
    this.threshold0 = 1;
    this.threshold1 = 1;
    this.csv = new HashMap<>();
    this.outputDir = "src/test/result2";
    this.dir = new File(outputDir);
    dir.mkdir();
    Map<Integer, Integer> row = new HashMap<>();
    row.put(2,2);
    row.put(9,3);
    csv.put("AAA_2013J",row );
    w1 = new WriteOutput(csv,outputDir,threshold0);
    w2 = new WriteOutput(csv,outputDir,threshold1);
  }

  @Test
  void run() {
    w1.run();
    w2.run();
  }
}