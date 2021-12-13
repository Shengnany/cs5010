import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ChatManagerTest {
  private static Socket client;
  private static ServerSocket server;
  private static Socket rcv;
  private static DataOutputStream dos;
  private static DataInputStream dis;
  private static ChatManager chatManager;

  @BeforeAll
  static void setUp() throws IOException {
    server = new ServerSocket(6666);
    client = new Socket("localhost", 6666);
    rcv = server.accept();
    dos = new DataOutputStream(client.getOutputStream());
    dis = new DataInputStream(client.getInputStream());
    byte[] nameBytes = "test".getBytes();
    dos.writeInt(Config.CONNECT_MESSAGE);
    dos.writeInt(nameBytes.length);
    dos.write(nameBytes);
    dos.flush();
    chatManager = new ChatManager(rcv);
    ChatManager.all.add(chatManager);
    new Thread(chatManager).start();
  }



  @Test
  void testQuery() throws IOException, InterruptedException {
//    ChatManager.all.add(chatManager);
//    new Thread(chatManager).start();
    byte[] nameBytes = "test".getBytes();
    dos.writeInt(Config.QUERY_CONNECTED_USERS);
    dos.writeInt(nameBytes.length);
    dos.write(nameBytes);
    dos.flush();
    Thread.sleep(1000);
  }

  @Test
  void testDirect() throws IOException {
//    ChatManager.all.add(chatManager);
//    new Thread(chatManager).start();
    byte[] nameBytes = "test".getBytes();
    dos.writeInt(Config.DIRECT_MESSAGE);
    dos.writeInt(nameBytes.length);
    dos.write(nameBytes);
    dos.writeInt(nameBytes.length);
    dos.write(nameBytes);
    dos.writeInt(nameBytes.length);
    dos.write(nameBytes);
    dos.flush();
  }


  @Test
  void testInsult() throws IOException {
//    ChatManager.all.add(chatManager);
//    new Thread(chatManager).start();
    byte[] nameBytes = "test".getBytes();
    dos.writeInt(Config.SEND_INSULT);
    dos.writeInt(nameBytes.length);
    dos.write(nameBytes);
    dos.writeInt(nameBytes.length);
    dos.write(nameBytes);
    dos.writeInt(nameBytes.length);
    dos.write(nameBytes);
    dos.flush();
  }

  @Test
  void testBroadcast() throws IOException {
//    ChatManager.all.add(chatManager);
//    new Thread(chatManager).start();
    byte[] nameBytes = "test".getBytes();
    dos.writeInt(Config.BROADCAST_MESSAGE);
    dos.writeInt(nameBytes.length);
    dos.write(nameBytes);
    dos.writeInt(nameBytes.length);
    dos.write(nameBytes);
    dos.flush();
  }

  @Test
  void testDisConnect() throws IOException, InterruptedException {
//    new Thread(chatManager).start();
    byte[] nameBytes = "test".getBytes();
    dos.writeInt(Config.DISCONNECT_MESSAGE);
    dos.writeInt(nameBytes.length);
    dos.write(nameBytes);
    dos.flush();
    Thread.sleep(1000);
  }

  @AfterAll
  static void close() throws IOException {
    client.close();
    server.close();
    rcv.close();
    dos.close();
    dis.close();
  }
}
