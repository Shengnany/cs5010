package concurrentSolution;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

/**
 * The type Read files.
 */
public class ReadFiles extends Thread{
  private final SynContainer container;
  private final FileReader filereader;

  /**
   * The Csv reader.
   */
// create csvReader object passing
  // file reader as a parameter
  CSVReader csvReader;

  /**
   * Instantiates a new Read files.
   *
   * @param container   the container
   * @param studentFile the student file
   * @throws FileNotFoundException the file not found exception
   */
  public ReadFiles(SynContainer container, File studentFile)
      throws FileNotFoundException {
    this.filereader = new FileReader(studentFile);
    this.csvReader =  new CSVReaderBuilder(filereader)
        .withSkipLines(1)
        .build();
    this.container = container;
  }

  @Override
  public void run(){
    String[] nextRecord;
    try{
      while ((nextRecord = csvReader.readNext()) != null) {
        String key = nextRecord[0]+"_"+nextRecord[1];
        int date = Integer.parseInt(nextRecord[4]);
        int count = Integer.parseInt(nextRecord[5]);
        Record record = new Record(key, date, count);
        container.add(record);
      }
      container.setEnd(true);
      return;
    }
    catch (CsvValidationException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
