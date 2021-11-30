package concurrentSolution;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AnalyzeSum {
  private final String[] args;
  private final String courseName;
  private final String studentVle;
  public AnalyzeSum(String[] args, String courseName, String studentVle) throws FileNotFoundException {
    this.args = args;
    this.courseName = courseName;
    this.studentVle = studentVle;
  }

  public void generate() throws FileNotFoundException {
    System.out.println("Please enter the output directory: ");
    Scanner sc = new Scanner(System.in);
    String outputDir = sc.nextLine();
    String inputDir = "src/main/resources";
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

    SynContainer container = new SynContainer();
    ReadFiles producer = new ReadFiles(container,course,student);
    AggregateOutput consumer1 = new AggregateOutput(container,outputDir);
    AggregateOutput consumer2 = new AggregateOutput(container,outputDir);
    AggregateOutput consumer3 = new AggregateOutput(container,outputDir);
    producer.start();
    consumer1.start();
    consumer2.start();
    consumer3.start();
  }


}
