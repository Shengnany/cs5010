import java.io.Closeable;

/**
 * The type Util.
 */
public class Util {

  /**
   * Close all.
   *
   * @param io the io
   */
  public static void closeAll(Closeable... io) {
    for (Closeable c : io) {
      try {
        if (c!= null) {
          c.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}