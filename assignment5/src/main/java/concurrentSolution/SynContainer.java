package concurrentSolution;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SynContainer {
  private Record[] records;
  private static final int BUF_SIZE = 2000;
  private int num;
  private boolean end;

  public SynContainer( ) {
    this.records = new Record[BUF_SIZE];
    this.num = 0;
    this.end = false;
  }

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

  public boolean isEnd() {
    return end;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SynContainer)) {
      return false;
    }
    SynContainer that = (SynContainer) o;
    return num == that.num && isEnd() == that.isEnd()
        && Arrays.equals(records, that.records);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(num, isEnd());
    result = 31 * result + Arrays.hashCode(records);
    return result;
  }

  @Override
  public String toString() {
    return "SynContainer{" +
        "records=" + Arrays.toString(records) +
        ", num=" + num +
        ", end=" + end
       ;
  }

  public synchronized void setEnd(boolean end) {
    this.end = end;
    this.notifyAll();
  }

  public Record[] getRecords() {
    return records;
  }
}
