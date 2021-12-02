package concurrentSolution;

import com.opencsv.exceptions.CsvException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class AnalyzeApp {

  private final static String courseName = "courses.csv";
  private final static String studentVle = "studentVle.csv";

  /**
   *
   * @param args the first argument will be the input directory, the second argument will be threshold(if exists)
   * @throws IOException
   * @throws CsvException
   * @throws InterruptedException
   */
  public static void main(String[] args) throws IOException, InterruptedException {
    int threshold = 0;
    if (args.length > 1) {
      threshold = Integer.parseInt(args[1]);
    }
    System.out.println("Please enter the output directory: ");
    Scanner sc = new Scanner(System.in);
    String outputDir = sc.nextLine().trim();
    String inputDir = args[0].trim();
    File dir = new File(inputDir);
    File course = null;
    File student = null;
    for (File f : dir.listFiles()) {
      if (f.getName().equals(courseName)) {
        course = f;
      } else if (f.getName().equals(studentVle)) {
        student = f;
      }
    }

    SynContainer container = new SynContainer();
    ReadFiles producer = new ReadFiles(container,  student);
    AggregateOutput consumer1 = new AggregateOutput(container, outputDir, threshold);

    producer.start();
    consumer1.start();
    producer.join();
    consumer1.join();
  }

}
