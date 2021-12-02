package sequentialSolution;

import com.opencsv.exceptions.CsvException;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

/**
 * The type Analyze app.
 */
public class AnalyzeApp {
  private final static String courseName = "courses.csv";
  private final static String studentVle = "studentVle.csv";

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   * @throws IllegalCLIArgException the illegal cli arg exception
   * @throws IOException            the io exception
   * @throws CsvException           the csv exception
   */
  public static void main(String[] args) throws IllegalCLIArgException, IOException, CsvException {
    if(args == null || args.length != 1)
      throw new IllegalCLIArgException("Please provide input directory");
    String inputDir = args[0].trim();
    File dir = new File(inputDir);
    File course = null;
    File student = null;
    for(File f: dir.listFiles()){
      if(f.getName().equals(courseName)){
        course = f;
      }
      else if(f.getName().equals(studentVle)){
        student = f;
      }
    }
    System.out.println("Please enter the output directory: ");
    Scanner sc = new Scanner(System.in);
    String outputDir = sc.nextLine();
    ReadCSV reader = new ReadCSV(course,student);
    Map<String, Map<Integer, Integer>> csv = reader.getCSVFiles();
    GenerateOutput g = new GenerateOutput();
    g.generate(csv, outputDir);

  }
}
