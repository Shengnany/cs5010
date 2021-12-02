package sequentialSolution;

import com.opencsv.CSVWriter;

import concurrentSolution.WriteOutput;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GenerateOutput {
  private String output;
  private Map<String, Map<Integer, Integer>> csv;
  private final static int COLUMN = 2;
  private final static String SUFFIX = ".csv";

  public GenerateOutput(String output) {
    this.output = output;
  }

  public void generate(Map<String, Map<Integer, Integer>> csv, String filePath){
    try {
      File dir =  new File(filePath);
      dir.mkdir();
      for(String key: csv.keySet()){
        Map<Integer,Integer> row = csv.get(key);
        List<String[]> data = new ArrayList<>();
        data.add(new String[] { "Date","Count" });
        String name = key+SUFFIX;
        File file = new File(dir,name);
        FileWriter outputfile = new FileWriter(file);
        write(row, data, outputfile, COLUMN);
      }
    }
    catch ( IOException e) {
      e.printStackTrace();
    }
  }
  public void write(Map<Integer, Integer> row, List<String[]> data, FileWriter outputfile,
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
