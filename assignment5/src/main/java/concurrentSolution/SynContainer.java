package concurrentSolution;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

public class SynContainer {
  private Record[] records;
  private static final int BUF_SIZE = 2000;
  private int num;
  private boolean end;
  private boolean endWrite;

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
    this.notifyAll();
  }

  public synchronized Record pop(){
    while(num <= 0){
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
    if (r == null) {
      System.out.println("++++++++++++++++++++++");
    }
    this.notifyAll();
    return r;
  }

  public boolean isEnd() {
    return end;
  }


  public void setEnd(boolean end) {
    this.end = end;
  }
}
