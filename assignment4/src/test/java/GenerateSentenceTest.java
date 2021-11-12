import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GenerateSentenceTest {
  GenerateSentence  gs;
  private Map<String, File> map;
  private Map<Integer, String> indexMap;
  @BeforeEach
  void setUp() {
    String path = "src/main/resources";
    map = ReadFile.getFileMap(path);
    indexMap = ReadFile.getIndexMap(map);
    File file = map.get(indexMap.get(new Random().nextInt(indexMap.size())));
    gs = new GenerateSentence(file);
  }

  @Test
  void testGenerate() {
    gs.generate();
  }
}