
package concurrentSolution;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AggregateOutput extends Thread{
  private SynContainer container;
  private String outputDir;
  private Map<String, Map<Integer, Integer>> csv;

  public AggregateOutput(SynContainer container, String outputDir) {
    this.container = container;
    this.outputDir = outputDir;
    this.csv = new HashMap<>();
  }

  @Override
  public void run(){
    while(true){
      Record r = container.pop();
//      System.out.println(r);
      if (container.isEnd()) {
        System.out.println("---------------------------------");
      }
      if(r == null) {
//        System.out.println(csv.toString());
        new Thread(new WriteOutput(csv,outputDir)).start();
        break;
      }
      Map<Integer, Integer> course = csv.getOrDefault(r.getKey(), new HashMap<>());
      course.put(r.getDate(),course.getOrDefault(r.getDate(),0) + r.getCount());
      csv.put(r.getKey(),course);
    }
    Thread.currentThread().interrupt();

  }
}
