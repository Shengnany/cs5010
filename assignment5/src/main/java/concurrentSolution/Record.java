package concurrentSolution;

import java.util.Objects;

/**
 * The type Record.
 */
public class Record {
  private String key;
  private int date;
  private int count;

  /**
   * Instantiates a new Record.
   *
   * @param key   the key
   * @param date  the date
   * @param count the count
   */
  public Record(String key, int date, int count){
    this.key = key;
    this.date = date;
    this.count = count;
  }

  /**
   * Gets key.
   *
   * @return the key
   */
  public String getKey() {
    return key;
  }

  /**
   * Gets date.
   *
   * @return the date
   */
  public int getDate() {
    return date;
  }

  /**
   * Gets count.
   *
   * @return the count
   */
  public int getCount() {
    return count;
  }
}
