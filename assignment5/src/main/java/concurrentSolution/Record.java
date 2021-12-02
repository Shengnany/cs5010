package concurrentSolution;

import java.util.Objects;

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
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Record)) {
      return false;
    }
    Record record = (Record) o;
    return getDate() == record.getDate() && getCount() == record.getCount() && getKey().equals(
        record.getKey());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getKey(), getDate(), getCount());
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
