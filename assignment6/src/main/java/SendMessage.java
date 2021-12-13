import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ProtocolException;
import java.net.Socket;

/**
 * This class will interact with command line input and send protocol to server
 */
public class SendMessage implements Runnable {
  private boolean isAlive;
  private BufferedReader consoleReader;
  private String name;
  private DataOutputStream dos;
  private byte[] nameBytes;

  /**
   * Instantiates a new Send message.
   *
   * @param client the client
   * @param name   the name
   */
  public SendMessage(Socket client, String name) {
    try{
      isAlive = true;
      consoleReader = new BufferedReader(new InputStreamReader(System.in));
      dos = new DataOutputStream(client.getOutputStream());
      this.name = name;
      nameBytes = name.getBytes();
      genProtocol("login");
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  /**
   * Gets msg from console.
   *
   * @return the msg from console
   */
  public String getMsgFromConsole() {
    try {
      return consoleReader.readLine();
    } catch (IOException e) {
      e.printStackTrace();

    }
    return "";
  }

  /**
   * Gen protocol.
   *
   * @param msg the msg
   */
  public void genProtocol(String msg) {
    try {
      if(msg != null && !msg.isEmpty()){
        msg = msg.trim();
        if(msg.equals("?")){
          showUsage();
        }
        else if (msg.startsWith("login")){
          send(Config.CONNECT_MESSAGE);
        }
        else if(msg.startsWith("logoff")){
          send(Config.DISCONNECT_MESSAGE);
        }
        else if(msg.startsWith("who")){
          send(Config.QUERY_CONNECTED_USERS);
        }
        else if(msg.startsWith("@all")){
          sendBroadCast(msg);
        }
        else if(msg.startsWith("@")){
          System.out.println(msg);
          if (msg.contains(" ")) {
            sendDirect(msg);
          } else {
            System.out.println("Incorrect message! Please @<existed_user>, seperated by space");
          }
        }
        else if(msg.startsWith("!")){
          sendInsult(msg);
        }
        else{
          showUsage();
        }
      }
    } catch (Exception | ChatProtocolException e) {
      e.printStackTrace();
    }
  }

  /**
   * Send insult.
   *
   * @param s the s
   * @throws ChatProtocolException the chat protocol exception
   */
  public void sendInsult(String s) throws ChatProtocolException {
    try {
      int type = Config.SEND_INSULT;                         //消息类型
      String to = s.substring(s.indexOf("!")+1).trim();
      byte[] toBytes = to.getBytes();
      int toLen = toBytes.length;
      int nameLen = nameBytes.length;
      dos.writeInt(type);
      dos.writeInt(nameLen);
      dos.write(nameBytes);
      dos.writeInt(toLen);
      dos.write(toBytes);
      dos.flush();
    } catch (IOException e) {
      e.printStackTrace();
      isAlive = false;
      Util.closeAll(dos,consoleReader);
      throw new ChatProtocolException("Incorrect format");
    }
  }

  /**
   * Send direct.
   *
   * @param s the s
   * @throws ChatProtocolException the chat protocol exception
   */
  public void sendDirect(String s) throws ChatProtocolException {
    try {
      int type = Config.DIRECT_MESSAGE;                         //消息类型
      String to = s.substring(s.indexOf("@")+1,s.indexOf(" ")).trim();
      String msg = s.substring(s.indexOf(" ")+1).trim();
      byte[] toBytes = to.getBytes();
      byte[] msgBytes = msg.getBytes();
      int toLen = toBytes.length;
      int nameLen = nameBytes.length;
      int msgLen = msgBytes.length;
      dos.writeInt(type);
      dos.writeInt(nameLen);
      dos.write(nameBytes);
      dos.writeInt(toLen);
      dos.write(toBytes);
      dos.writeInt(msgLen);
      dos.write(msgBytes);
      dos.flush();
      System.out.println(name + " -> " + to + " " + msg );
    } catch (IOException e) {
      e.printStackTrace();
      isAlive = false;
      Util.closeAll(dos,consoleReader);
      throw new ChatProtocolException("Incorrect format");
    }
  }

  /**
   * Send broad cast.
   *
   * @param msg the msg
   * @throws ChatProtocolException the chat protocol exception
   */
  public void sendBroadCast(String msg) throws ChatProtocolException {
    try {
      msg = msg.substring(msg.indexOf("@all")+"@all".length()+1).trim();
      int type = Config.BROADCAST_MESSAGE;
      byte[] msgBytes = msg.getBytes();
      int nameLen = nameBytes.length;
      int msgLen = msgBytes.length;
      dos.writeInt(type);
      dos.writeInt(nameLen);
      dos.write(nameBytes);
      dos.writeInt(msgLen);
      dos.write(msgBytes);
      dos.flush();
      System.out.println(name + " -> " + "everyone" + " " + msg );
    } catch (IOException e) {
      e.printStackTrace();
      isAlive = false;
      Util.closeAll(dos,consoleReader);
      throw new ChatProtocolException("Incorrect format");
    }
  }


  /**
   * Send.
   *
   * @param type the type
   * @throws ChatProtocolException the chat protocol exception
   */
  public void send(int type) throws ChatProtocolException {
    try {
      int totalLen = nameBytes.length;
      dos.writeInt(type);
      dos.writeInt(totalLen);
      dos.write(nameBytes);
      dos.flush();
    } catch (IOException e) {
      e.printStackTrace();
      isAlive = false;
      Util.closeAll(dos,consoleReader);
      throw new ChatProtocolException("Incorrect format");

    }
  }

  /**
   * Show usage.
   */
  public void showUsage() {
    System.out.println("Main Menu: " +
        "\nlogoff: sends a DISCONNECT_MESSAGE to the server." +
        "\nwho:    sends a QUERY_CONNECTED_USERS to the server." +
        "\n@user:  sends a DIRECT_MESSAGE to the specified user to the server." +
        "\n@all:   sends a BROADCAST_MESSAGE to the server, to be sent to all users connected." +
        "\n!user:  sends a SEND_INSULT message to the server, to be sent to the specified user.");
  }


  @Override
  public void run() {
    while (isAlive) {
      try {
        Thread.sleep(100);
        System.out.println("Enter an option: ");
        genProtocol(getMsgFromConsole());
      } catch (Exception e) {
        e.printStackTrace();
      }

    }
  }
}
