package sequentialSolution;

import static org.junit.jupiter.api.Assertions.*;

import com.opencsv.exceptions.CsvException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReadCSVTest {
  private String courseFilePath;
  private String studentFilePath;
  File courseFile;
  File studentFile;
  private Map<String, Map<Integer, Integer>> csv;
  private ReadCSV r;


  @BeforeEach
  void setUp() throws IOException, CsvException {
    courseFilePath = "resources/courses.csv";
    studentFilePath = "resources/studentVle.csv";
    courseFile = new File(courseFilePath);
    studentFile = new File(studentFilePath);
    csv = new HashMap<>();
    r = new ReadCSV(courseFile, studentFile);
  }
}