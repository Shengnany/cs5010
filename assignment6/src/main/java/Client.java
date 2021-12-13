import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * The type Client.
 */
public class Client {
  private static int server_port;
  private static String server_host;

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   * @throws IOException the io exception
   */
  public static void main(String[] args) throws IOException {
    server_host = args[0].trim();
    server_port = Integer.parseInt(args[1]);
    System.out.println("Please Enter your name:");
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String name = br.readLine();
    if (name.isEmpty()) {
      System.out.println("No name provided");
      return;
    }
    Socket client = new Socket(server_host, server_port);
    Thread rcv = new Thread(new ReceiveMessage(client,name));
    Thread snd =  new Thread(new SendMessage(client, name));
    rcv.start();
    snd.start();
  }

}
