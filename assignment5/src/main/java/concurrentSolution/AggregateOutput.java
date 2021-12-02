package concurrentSolution;

import java.awt.Container;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The type Aggregate output.
 */
public class AggregateOutput extends Thread{
  private final SynContainer container;
  private final int threshold;
  private final String outputDir;
  private final Map<String, Map<Integer, Integer>> csv;

  /**
   * Instantiates a new Aggregate output.
   *
   * @param container the container
   * @param outputDir the output dir
   * @param threshold the threshold
   */
  public AggregateOutput(SynContainer container, String outputDir, int threshold) {
    this.container = container;
    this.outputDir = outputDir;
    this.csv = new HashMap<>();
    this.threshold = threshold;
  }

  @Override
  public void run(){
    while(true){
      Record r = container.pop();
      if(r == null) {
        new Thread(new WriteOutput(csv,outputDir,threshold)).start();
        break;
      }
      Map<Integer, Integer> course = csv.getOrDefault(r.getKey(), new HashMap<>());
      course.put(r.getDate(),course.getOrDefault(r.getDate(),0) + r.getCount());
      csv.put(r.getKey(),course);
    }
    return;
  }
}
