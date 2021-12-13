import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * This class will receive protocol from server and show in command line output
 */
public class ReceiveMessage implements Runnable {

  private boolean isAlive;
  private DataInputStream dis;
  private Socket client;
  private String name;

  /**
   * Instantiates a new Receive message.
   *
   * @param client the client
   * @param name   the name
   */
  public ReceiveMessage(Socket client, String name) {
    try {
      this.client = client;
      this.isAlive = true;
      this.name = name;

      dis = new DataInputStream(client.getInputStream());
    } catch (IOException e) {
      e.printStackTrace();
      isAlive = false;
      Util.closeAll(dis);
    }


  }

  @Override
  public void run() {
    while (isAlive) {
      printMsg();
      if (!isAlive) {
        System.out.println("You can exit the program now");
      }
    }
  }

  private void printMsg() {
    int type = -1;
    try {
      type = dis.readInt();
      switch (type) {
        case Config.CONNECT_RESPONSE:
          connect();
          break;
        case Config.DISCONNECT_MESSAGE:
          disconnect();
          break;
        case Config.QUERY_USER_RESPONSE:
          query();
          break;
        case Config.BROADCAST_MESSAGE:
          broadCast();
          break;
        case Config.DIRECT_MESSAGE:
          directMsg();
          break;
        case Config.FAILED_MESSAGE:
          fail();
          break;
        default:
          System.out.println("Wrong type");
          break;
//        case Config.SEND_INSULT:
//          insult();
//          break;
      }

    } catch (IOException e) {
//      e.printStackTrace();
      isAlive = false;
      Util.closeAll(dis);
    }
  }

  /**
   * Fail.
   *
   * @throws IOException the io exception
   */
  public void fail() throws IOException {
    System.out.println(readStr(dis,dis.readInt()));
  }

  /**
   * Direct msg.
   *
   * @throws IOException the io exception
   */
  public void directMsg() throws IOException {
    String msg = "";
    msg += readStr(dis,dis.readInt()) + " -> ";
    msg += readStr(dis,dis.readInt()) + " : ";
    msg += readStr(dis,dis.readInt());
    System.out.println(msg);
  }

  /**
   * Broad cast.
   *
   * @throws IOException the io exception
   */
  public void broadCast() throws IOException {
    String msg = "";
    msg += readStr(dis,dis.readInt()) + " -> " + "everyone: ";
    msg += readStr(dis,dis.readInt());
    System.out.println(msg);
  }

  /**
   * Query.
   *
   * @throws IOException the io exception
   */
  public void query() throws IOException {
    String query = "ALive users : ";
    int numberOfUsers = dis.readInt();
    for(int i=0; i<numberOfUsers; i++){
      query += readStr(dis,dis.readInt()) + ",";
    }
    System.out.println(query);

  }

  /**
   * Disconnect.
   *
   * @throws IOException the io exception
   */
  public void disconnect() throws IOException {
    System.out.println("Bye");
    System.out.println(readStr(dis,dis.readInt()));
    this.isAlive = false;

  }

  /**
   * Connect.
   *
   * @throws IOException the io exception
   */
  public void connect() throws IOException {
    boolean success = dis.readBoolean();
    System.out.println(success ? "Connection success! ": "Connection refused! ");
    System.out.println(readStr(dis,dis.readInt()));
  }

  /**
   * Read str string.
   *
   * @param dis    the dis
   * @param length the length
   * @return the string
   * @throws IOException the io exception
   */
  public String readStr(DataInputStream dis, int length) throws IOException {
    byte[] bytes = new byte[length];
    dis.readFully(bytes);
    return new String(bytes, StandardCharsets.UTF_8);
  }
}
