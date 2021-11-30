package concurrentSolution;
public class Record {
  private String key;
  private int date;
  private int count;
  public Record(String key, int date, int count){
    this.key = key;
    this.date = date;
    this.count = count;
  }

  public String getKey() {
    return key;
  }

  public int getDate() {
    return date;
  }

  public int getCount() {
    return count;
  }

  @Override
  public String toString() {
    return "Record{" +
        "key='" + key + '\'' +
        ", date=" + date +
        ", count=" + count +
        '}';
  }
}
