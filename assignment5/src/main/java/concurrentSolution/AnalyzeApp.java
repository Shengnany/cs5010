package concurrentSolution;
import com.opencsv.exceptions.CsvException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class AnalyzeApp {
  private final static String courseName = "courses.csv";
  private final static String studentVle = "studentVle.csv";
  public static void main(String[] args) throws IOException, CsvException, InterruptedException {
    if(args.length == 1){
      AnalyzeSum sum = new AnalyzeSum(args, courseName, studentVle);
      sum.generate();
    }
    else{

    }

  }
}
