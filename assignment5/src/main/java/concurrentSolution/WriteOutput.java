package concurrentSolution;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import jdk.jfr.Threshold;

public class WriteOutput implements Runnable {
  private final int threshold;
  private final String[] HEADER = {"Date", "Count"};
  private final String[] HEADER_TD = {"Course", "Date", "Count"};
  private final Map<String, Map<Integer, Integer>> csv;
  private final String outputDir;
  private final File dir;
  private final static String SUFFIX = ".csv";
  private final static String PREFIX = "activity-";
  private final static int COLUMN = 2;
  private final static int COLUMN_THRESHOLD = 3;

  public WriteOutput(Map<String, Map<Integer, Integer>> csv, String outputDir, int threshold) {
    this.threshold = threshold;
    this.csv = csv;
    this.outputDir = outputDir;
    this.dir = new File(outputDir);
    dir.mkdir();
  }

  @Override
  public void run() {

    try {
      if (threshold == 0) {
        generateFiles();
      } else {
        generateFile();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void generateFile() throws IOException {
      String[] header = new String[COLUMN_THRESHOLD];
      List<String[]> data = new ArrayList<>();
      data.add(HEADER_TD);
      String name = PREFIX + String.valueOf(threshold)+SUFFIX;
      File file = new File(dir, name);
      FileWriter outputfile = null;
      outputfile = new FileWriter(file);
      writeFile(data, outputfile, COLUMN_THRESHOLD);
    }

  private void generateFiles() throws IOException {
    for (String key : csv.keySet()) {
      Map<Integer, Integer> row = csv.get(key);
      List<String[]> data = new ArrayList<>();
      {
        data.add(HEADER);
        String name = key + SUFFIX;
        File file = new File(dir, name);
        FileWriter outputfile = null;
        outputfile = new FileWriter(file);
        write(row, data, outputfile, COLUMN, key);
      }
    }
  }

  private void writeFile(List<String[]> data, FileWriter outputfile, int column)
      throws IOException {
      CSVWriter writer = new CSVWriter(outputfile);
      for (String key : csv.keySet()) {
        Map<Integer, Integer> row =  csv.get(key);
        for (Integer date: row.keySet()) {
          String[] cell = new String[column];
          cell[0] = key;
          cell[1] = String.valueOf(date);
          cell[2] = String.valueOf(row.get(date));
          data.add(cell);
        }
      }
      writer.writeAll(data);
      writer.close();
  }



  private void write(Map<Integer, Integer> row, List<String[]> data, FileWriter outputfile, int column, String key
     ) throws IOException {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof WriteOutput)) {
      return false;
    }
    WriteOutput that = (WriteOutput) o;
    return threshold == that.threshold && Arrays.equals(HEADER, that.HEADER)
        && Arrays.equals(HEADER_TD, that.HEADER_TD) && Objects.equals(csv,
        that.csv) && Objects.equals(outputDir, that.outputDir) && Objects.equals(
        dir, that.dir);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(threshold, csv, outputDir, dir);
    result = 31 * result + Arrays.hashCode(HEADER);
    result = 31 * result + Arrays.hashCode(HEADER_TD);
    return result;
  }

  @Override
  public String toString() {
    return "WriteOutput{" +
        "threshold=" + threshold +
        ", HEADER=" + Arrays.toString(HEADER) +
        ", HEADER_TD=" + Arrays.toString(HEADER_TD) +
        ", csv=" + csv +
        ", outputDir='" + outputDir + '\'' +
        ", dir=" + dir +
        '}';
  }
}
