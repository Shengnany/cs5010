package concurrentSolution;

import java.awt.Container;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AggregateOutput extends Thread{
  private final SynContainer container;
  private final int threshold;
  private final String outputDir;
  private final Map<String, Map<Integer, Integer>> csv;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof AggregateOutput)) {
      return false;
    }
    AggregateOutput that = (AggregateOutput) o;
    return threshold == that.threshold && Objects.equals(container, that.container)
        && Objects.equals(outputDir, that.outputDir) && Objects.equals(csv,
        that.csv);
  }

  @Override
  public int hashCode() {
    return Objects.hash(container, threshold, outputDir, csv);
  }
}
