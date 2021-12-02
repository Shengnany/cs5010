package sequentialSolution;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GenerateOutputTest {
  private String filePath;
  private Map<String, Map<Integer, Integer>> csv;
  private final static int COLUMN = 2;
  private final static String SUFFIX = ".csv";
  private GenerateOutput g1;


  @BeforeEach
  void setUp() {
    this.csv = new HashMap<>();
    this.filePath = "src/test/resources/result_seq_app";
    File dir = new File(filePath);
    dir.mkdir();
    Map<Integer, Integer> row = new HashMap<>();
    row.put(2,2);
    row.put(9,3);
    csv.put("AAA_2013J", row);
    g1 = new GenerateOutput();
  }

  @Test
  void generate() {
    g1.generate(csv, filePath);
    File dir = new File(filePath);
    File file = new File(dir, "AAA_2013J.csv");
    Assertions.assertTrue(file.exists());
  }

}