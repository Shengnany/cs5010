package concurrentSolution;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * The type Syn container.
 */
public class SynContainer {
  private Record[] records;
  private static final int BUF_SIZE = 2000;
  private int num;
  private boolean end;

  /**
   * Instantiates a new Syn container.
   */
  public SynContainer( ) {
    this.records = new Record[BUF_SIZE];
    this.num = 0;
    this.end = false;
  }

  /**
   * Add.
   *
   * @param r the r
   */
  public synchronized void add(Record r){
    while(num >= records.length){
      try{
        this.wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
//    System.out.println(r);
    records[num] = r;
    num++;
//    System.out.println("add" + Thread.currentThread().getName() + " " + num);
    this.notifyAll();
  }

  /**
   * Pop record.
   *
   * @return the record
   */
  public synchronized Record pop(){
    while(num <= 0){
//      System.out.println(this.isEnd() +" when num <=0" + num + Thread.currentThread().getName());
      if(this.isEnd()) {

        System.out.println("end agggregate");
        this.notifyAll();
        return null;
      };
      try{
        this.wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    num--;

    Record r = records[num];
    this.notifyAll();
    return r;
  }

  /**
   * Is end boolean.
   *
   * @return the boolean
   */
  public boolean isEnd() {
    return end;
  }

  /**
   * Sets end.
   *
   * @param end the end
   */
  public synchronized void setEnd(boolean end) {
    this.end = end;
    this.notifyAll();
  }

  /**
   * Get records record [ ].
   *
   * @return the record [ ]
   */
  public Record[] getRecords() {
    return records;
  }
}
