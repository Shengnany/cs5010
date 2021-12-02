package sequentialSolution;

import static org.junit.jupiter.api.Assertions.*;

import com.opencsv.exceptions.CsvException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AnalyzeAppTest {

  /**
   *   private final static String courseName = "courses.csv";
   *   private final static String studentVle = "studentVle.csv";
   *   public static void main(String[] args) throws IllegalCLIArgException, IOException, CsvException {
   *     if(args == null || args.length != 1)
   *       throw new IllegalCLIArgException("Please provide input directory");
   *     String inputDir = args[0].trim();
   *     File dir = new File(inputDir);
   *     File course = null;
   *     File student = null;
   *     for(File f: dir.listFiles()){
   *       if(f.getName().equals(courseName)){
   *         course = f;
   *       }
   *       else if(f.getName().equals(studentVle)){
   *         student = f;
   *       }
   *     }
   *     System.out.println("Please enter the output directory: ");
   *     Scanner sc = new Scanner(System.in);
   *     String outputDir = sc.nextLine();
   *     ReadCSV reader = new ReadCSV(course,student);
   *     Map<String, Map<Integer, Integer>> csv = reader.getCSVFiles();
   *     GenerateOutput g = new GenerateOutput();
   *     g.generate(csv, outputDir);
   *
   *   }
   */
  @BeforeEach
  void setUp() {
  }

  @Test
  void main() throws IOException, InterruptedException, IllegalCLIArgException, CsvException {
    File dir = new File("src/test/resources/result_seq_app");
    dir.mkdir();
    String[] args = {"src/test/resources"};
    String str = "src/test/resources/result_seq_app\n";
    ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes());
    System.setIn(in);
    AnalyzeApp.main(args);
  }
}