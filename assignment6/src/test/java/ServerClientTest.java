import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *   public static void main(String[] args) throws IOException {
 *     ServerSocket server = new ServerSocket(PORT);
 *     System.out.println("Server "+ server.getInetAddress() + " is listening on port " + PORT);
 *     while (true) {
 *       Socket client = server.accept();
 *       System.out.println("New client request received : " + client.getInetAddress().getHostAddress());
 *       System.out.println("Creating a new handler for this client...");
 *       ChatManager channel = new ChatManager(client);
 *       ChatManager.all.add(channel);
 *       new Thread(channel).start();
 *       System.out.println("There are " + ChatManager.all.size() + " users in total. ");
 *     }
 *   }
   * }
   */
  class ServerClientTest {
//  private ByteArrayOutputStream bytes = null;
//
//  @BeforeEach
//  void setUp() {
//    bytes = new ByteArrayOutputStream();
//    System.setOut();
//  }

  @Test
  void test() throws InterruptedException {
    Thread server = new Thread(() -> {
      try {
        Server.main(new String[]{});
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    server.start();
    Thread.sleep(1000);
    new Thread(() -> {
      String input = "a\n";
      InputStream in = new ByteArrayInputStream(input.getBytes());
      System.setIn(in);
      String[] args = new String[]{"localhost", "8888"};
      try {
        Client.main(args);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }).start();
    server.interrupt();
  }
}