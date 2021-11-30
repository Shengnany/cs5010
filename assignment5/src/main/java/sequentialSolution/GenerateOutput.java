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
  private Map<String, Map<String, Integer>> csv;
  private final static int COLUMN = 2;
  private final static String SUFFIX = ".csv";

  public GenerateOutput(String output) {
    this.output = output;
  }

  public void generate(Map<String, Map<Integer, Integer>> csv, String filePath){

    try {
//      CSVWriter writer = null;
      File dir =  new File(filePath);
      dir.mkdir();
      for(String key: csv.keySet()){
        Map<Integer,Integer> row = csv.get(key);
        List<String[]> data = new ArrayList<>();
        data.add(new String[] { "Date","Count" });
        String name = key+SUFFIX;
        File file = new File(dir,name);
        FileWriter outputfile = new FileWriter(file);
        WriteOutput.write(row, data, outputfile, COLUMN);
      }
//      if(writer != null){
//        writer.close();
//      }
    }
    catch ( IOException e) {
      e.printStackTrace();
    }
  }
}
