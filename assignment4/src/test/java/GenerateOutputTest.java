import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GenerateOutputTest {
  GenerateOutput go;
  private Map<String, File> map;
  private Map<Integer, String> indexMap;

  @BeforeEach
  void setUp() {
    map = new HashMap<>();
    indexMap = new HashMap<>();
    go = new GenerateOutput(map,indexMap);
  }


  @Test
  void dealWithOutput1() throws InvalidArgumentException {
    RandomSentenceGenerator r = new RandomSentenceGenerator();
    r.main(new String[]{"src/main/resources"});
    String str = "1\n";
    str +=  "q\n";
    ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes());
    System.setIn(in);


  }

  //error: y/n ->1
  @Test
  void dealWithOutput2() throws InvalidArgumentException {
    RandomSentenceGenerator r = new RandomSentenceGenerator();
    r.main(new String[]{"src/main/resources"});
    String str = "1\n";
    str += "1\n";
    str += "y\n";
    str += "q\n";
    ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes());
    System.setIn(in);
  }

  @Test
  void dealWithOutput3() {
    go.dealWithOutput();
    String str = "q\n";
    ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes());
    System.setIn(in);
  }

  //1->y/n
  @Test
  void dealWithOutput4() {
    go.dealWithOutput();
    String str = "y\n";
    str += "1\n";
    ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes());
    System.setIn(in);
  }


  //1->y/n
  @Test
  void dealWithOutput5() {
    go.dealWithOutput();
    String str = "3\n";
    str += "y\n";
    str += "n\n";
    str += "q\n";
    ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes());
    System.setIn(in);

  }
}