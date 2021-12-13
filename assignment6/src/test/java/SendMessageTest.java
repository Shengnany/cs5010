import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SendMessageTest {
  private Socket client1;
  private Socket client2;
  private ServerSocket server;

  @BeforeEach
  void setup() throws IOException, InterruptedException {
    server = new ServerSocket(8080);
    Thread.sleep(1000);
    client1 = new Socket("localhost",8080);
    client2 = new Socket("localhost",8080);
  }

  @Test
  void testWho() throws InterruptedException {
    String input = "who\n";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
    SendMessage send = new SendMessage(client1, "x");
    new Thread(send).start();
    Thread.sleep(1000);
  }

  @Test
  void testUsage() throws InterruptedException {
    String input = "?\n";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
    SendMessage send = new SendMessage(client1, "x");
    new Thread(send).start();
    Thread.sleep(1000);
  }

  @Test
  void testBroadcast() throws InterruptedException {
    String input = "@all 123\n";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
    SendMessage send = new SendMessage(client1, "x");
    new Thread(send).start();
    Thread.sleep(1000);
  }

  @Test
  void testDirect() throws InterruptedException {
    String input = "@123 123\n";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
    SendMessage send = new SendMessage(client1, "x");
    new Thread(send).start();
    Thread.sleep(1000);
  }

  @Test
  void testInsult() throws InterruptedException {
    String input = "!123\n";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
    SendMessage send = new SendMessage(client1, "x");
    new Thread(send).start();
    Thread.sleep(1000);
  }

  @AfterEach
  void close() throws IOException {
    client1.close();
    client2.close();
    server.close();
  }
}