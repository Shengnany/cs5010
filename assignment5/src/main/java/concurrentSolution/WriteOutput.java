package concurrentSolution;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WriteOutput implements Runnable {

  private final Map<String, Map<Integer, Integer>> csv;
  private final String outputDir;
  private final File dir;
  private final static String SUFFIX = ".csv";
  private final static int COLUMN = 2;

  public WriteOutput(Map<String, Map<Integer, Integer>> csv, String outputDir) {
    this.csv = csv;
    this.outputDir = outputDir;
    this.dir = new File(outputDir);
    dir.mkdir();
  }

  @Override
  public void run() {
    for (String key : csv.keySet()) {
      try {
        System.out.println("starting write");
        Map<Integer, Integer> row = csv.get(key);
        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"Date", "Count"});
        String name = key + SUFFIX;
        File file = new File(dir, name);
        FileWriter outputfile = null;
        outputfile = new FileWriter(file);
        write(row, data, outputfile, COLUMN);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static void write(Map<Integer, Integer> row, List<String[]> data, FileWriter outputfile,
      int column) throws IOException {
    System.out.println("write");
    CSVWriter writer = new CSVWriter(outputfile);
    for (Integer date : row.keySet()) {
      String[] cell = new String[column];
      cell[0] = String.valueOf(date);
      cell[1] = String.valueOf(row.get(date));
      data.add(cell);
    }
    writer.writeAll(data);
    writer.close();
  }
}
