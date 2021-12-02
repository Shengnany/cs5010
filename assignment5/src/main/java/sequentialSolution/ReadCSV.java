package sequentialSolution;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import concurrentSolution.Record;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Read csv.
 */
public class ReadCSV {
  private File courseFile;
  private File studentFile;
  private Map<String, Map<Integer, Integer>> csv;

  /**
   * Instantiates a new Read csv.
   *
   * @param courseFile  the course file
   * @param studentFile the student file
   * @throws IOException  the io exception
   * @throws CsvException the csv exception
   */
  public ReadCSV(File courseFile, File studentFile) throws IOException, CsvException {
    this.courseFile = courseFile;
    this.studentFile = studentFile;
    this.csv = new HashMap<String, Map<Integer, Integer>>();
  }

  /**
   * Gets csv files.
   *
   * @return the csv files
   * @throws IOException  the io exception
   * @throws CsvException the csv exception
   */
  public Map<String, Map<Integer, Integer>> getCSVFiles() throws IOException, CsvException {
    readCourse();
    countCourse();
    return csv;
  }

  /**
   * @throws IOException  IOException
   * @throws CsvException CsvException
   */
  private void readCourse() throws IOException, CsvException {
    FileReader filereader = new FileReader(courseFile);
    CSVReader csvReader = new CSVReaderBuilder(filereader)
        .withSkipLines(1)
        .build();
    String[] nextRecord;
    while ((nextRecord = csvReader.readNext()) != null) {
      String key = nextRecord[0]+"_"+nextRecord[1];
      csv.put(key, new HashMap<Integer, Integer>());
    }
  }

  /**
   * @throws IOException  IOException
   * @throws CsvException CsvException
   */
  private void countCourse() throws IOException, CsvException {
    FileReader filereader = new FileReader(studentFile);
    CSVReader csvReader = new CSVReaderBuilder(filereader)
        .withSkipLines(1)
        .build();
    String[] nextRecord;
    while ((nextRecord = csvReader.readNext()) != null) {
      String key = nextRecord[0]+"_"+nextRecord[1];
      Map<Integer,Integer> map = csv.get(key);
      int date = Integer.parseInt(nextRecord[4]);
      map.put(date,map.getOrDefault(date,0)+Integer.parseInt(nextRecord[5]));
    }
  }
}
