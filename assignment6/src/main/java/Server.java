import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The type Server.
 */
public class Server {
  private final static int PORT = 8888;

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   * @throws IOException the io exception
   */
  public static void main(String[] args) throws IOException {
    ServerSocket server = new ServerSocket(PORT);
    System.out.println("Server "+ server.getInetAddress() + " is listening on port " + PORT);
    while (true) {
      Socket client = server.accept();
      System.out.println("New client request received : " + client.getInetAddress().getHostAddress());
      System.out.println("Creating a new handler for this client...");
      ChatManager channel = new ChatManager(client);
      ChatManager.all.add(channel);
      new Thread(channel).start();
      System.out.println("There are " + ChatManager.all.size() + " users in total. ");
    }
  }
}