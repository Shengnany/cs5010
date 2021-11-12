import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ReadFileTest {

  @Test
  void getFileMap() {
    String path = "src/main/resources";
    Map<String, File> res = ReadFile.getFileMap(path);
    assertTrue(res.size()==3);

  }

  @Test
  void getIndexMap() {
    String path = "src/main/resources";
    Map<String, File> map = ReadFile.getFileMap(path);
    Map<Integer, String> res = ReadFile.getIndexMap(map);
    assertTrue(res.size()==3);
  }
}