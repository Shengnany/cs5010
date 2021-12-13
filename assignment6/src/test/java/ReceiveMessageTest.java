import static org.junit.jupiter.api.Assertions.*;


import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReceiveMessageTest {

  private Socket client;
  private ServerSocket server;
  private Socket rcv;
  private DataOutputStream dosRcv;

  @BeforeEach
  void setUp() throws IOException {
    server = new ServerSocket(7777);
    client = new Socket("localhost", 7777);
    rcv = server.accept();
    dosRcv = new DataOutputStream(rcv.getOutputStream());
  }

  @Test
  void testConnectResponse() throws IOException, InterruptedException {
    byte[] msg = "test".getBytes(StandardCharsets.UTF_8);
    dosRcv.writeInt(Config.CONNECT_RESPONSE);
    dosRcv.writeBoolean(true);
    dosRcv.writeInt(msg.length);
    dosRcv.write(msg);
    dosRcv.flush();
    ReceiveMessage ReceiveMessage = new ReceiveMessage(client, "test");
    new Thread(ReceiveMessage).start();
    Thread.sleep(1000);
  }

  @Test
  void testDisconnect() throws IOException, InterruptedException {
    byte[] msg = "test".getBytes(StandardCharsets.UTF_8);
    dosRcv.writeInt(Config.DISCONNECT_MESSAGE);
    dosRcv.writeInt(msg.length);
    dosRcv.write(msg);
    dosRcv.flush();
    ReceiveMessage ReceiveMessage = new ReceiveMessage(client, "test");
    new Thread(ReceiveMessage).start();
    Thread.sleep(1000);
  }

  @Test
  void testQuery() throws IOException, InterruptedException {
    byte[] msg = "test".getBytes(StandardCharsets.UTF_8);
    dosRcv.writeInt(Config.QUERY_USER_RESPONSE);
    dosRcv.writeInt(2);
    dosRcv.writeInt(msg.length);
    dosRcv.write(msg);
    dosRcv.writeInt(msg.length);
    dosRcv.write(msg);
    dosRcv.flush();
    ReceiveMessage ReceiveMessage = new ReceiveMessage(client, "test");
    new Thread(ReceiveMessage).start();
    Thread.sleep(1000);
  }

  @Test
  void testBroadcast() throws IOException, InterruptedException {
    byte[] msg = "test".getBytes(StandardCharsets.UTF_8);
    dosRcv.writeInt(Config.BROADCAST_MESSAGE);
    dosRcv.writeInt(msg.length);
    dosRcv.write(msg);
    dosRcv.writeInt(msg.length);
    dosRcv.write(msg);
    dosRcv.flush();
    ReceiveMessage ReceiveMessage = new ReceiveMessage(client, "test");
    new Thread(ReceiveMessage).start();
    Thread.sleep(1000);
  }

  @Test
  void testDirect() throws IOException, InterruptedException {
    byte[] msg = "test".getBytes(StandardCharsets.UTF_8);
    dosRcv.writeInt(Config.DIRECT_MESSAGE);
    dosRcv.writeInt(msg.length);
    dosRcv.write(msg);
    dosRcv.writeInt(msg.length);
    dosRcv.write(msg);
    dosRcv.writeInt(msg.length);
    dosRcv.write(msg);
    dosRcv.flush();
    ReceiveMessage ReceiveMessage = new ReceiveMessage(client, "test");
    new Thread(ReceiveMessage).start();
    Thread.sleep(1000);
  }

  @Test
  void testFail() throws IOException, InterruptedException {
    byte[] msg = "test".getBytes(StandardCharsets.UTF_8);
    dosRcv.writeInt(Config.FAILED_MESSAGE);
    dosRcv.writeInt(msg.length);
    dosRcv.write(msg);
    dosRcv.flush();
    ReceiveMessage ReceiveMessage = new ReceiveMessage(client, "test");
    new Thread(ReceiveMessage).start();
    Thread.sleep(1000);
  }

  @AfterEach
  void close() throws IOException {
    client.close();
    server.close();
    rcv.close();
    dosRcv.close();
  }
}