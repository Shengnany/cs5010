import assignment4.GenerateSentence;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This class will receive protocol, parse as string and send protocol
 */
public class ChatManager implements Runnable {

  /**
   * The constant all.
   */
  public static List<ChatManager> all = new CopyOnWriteArrayList<>();
  private Socket client;
  private DataInputStream dis;
  private DataOutputStream dos;
  private String name;
  private boolean isAlive;
  private byte[] buf;

  /**
   * Initialize a channel and send start messages
   *
   * @param client the specific channel for the client
   */
  public ChatManager(Socket client) {
    try {
      this.client = client;
      isAlive = true;
      dis = new DataInputStream(client.getInputStream());
      dos = new DataOutputStream(client.getOutputStream());
      parseProtocol();
//      this.name = dis.readUTF();
//      this.send(this.name + "，welcome to the room");
//      prepareMsg(this.name + "entered the room", true); // 系统消息
    } catch (IOException e) {
      e.printStackTrace();
      Util.closeAll(dis, dos);
      isAlive = false;
    }
  }


  /**
   * read the specific client's input
   * @return the msg
   */
  private  void parseProtocol() {
    try {
      int type = dis.readInt();
      System.out.println(type);
      switch (type){
        case Config.CONNECT_MESSAGE:
          System.out.println(genCon());
          break;
        case Config.DISCONNECT_MESSAGE:
          genDiscon();
          all.remove(this);
          System.out.println(name+" has left the room");
          isAlive = false;
          break;
        case Config.QUERY_CONNECTED_USERS:
          genQueRsp();
          break;
        case Config.BROADCAST_MESSAGE:
          genMsg(true);
          break;
        case Config.DIRECT_MESSAGE:
          genMsg(false);
          break;
        case Config.SEND_INSULT:
          genInsult();
          break;
        default:
          genFail("Fail message: Wrong Type! ");
      }
    } catch (IOException e) {
      e.printStackTrace();
      Util.closeAll(dis);
      isAlive = false;
      all.remove(this);
    }
  }

  private void genInsult() throws IOException {
    String sender = readString(dis,dis.readInt());
    String rcp = readString(dis,dis.readInt());
    byte[] userBytes =  sender.getBytes();
    byte[] rcpBytes = rcp.getBytes(StandardCharsets.UTF_8);
    File file = new File("src/main/resources/insult_grammar.json");
    GenerateSentence generateSentence = new GenerateSentence(file);
    String insultSentence = generateSentence.generate();
    byte[] msgBytes = insultSentence.getBytes(StandardCharsets.UTF_8);
    for(ChatManager chat: ChatManager.all){
      chat.dos.writeInt(Config.DIRECT_MESSAGE);
      chat.dos.writeInt(userBytes.length);
      chat.dos.write(userBytes);
      chat.dos.writeInt(rcpBytes.length);
      chat.dos.write(rcpBytes);
      chat.dos.writeInt(msgBytes.length);
      chat.dos.write(msgBytes);
      chat.dos.flush();
    }

  }

  private synchronized void genFail(String msg) throws IOException {
    byte[] msgBytes = msg.getBytes();
    this.dos.writeInt(Config.FAILED_MESSAGE);
    this.dos.writeInt(msgBytes.length);
    this.dos.write(msgBytes);
  }

  private synchronized void genMsg(boolean broadcast) throws IOException {
    String sender = readString(dis,dis.readInt());
    if (broadcast){
      String msg = readString(dis,dis.readInt());
      for (ChatManager other : all) {
        if (other == this) {
          continue;
        }
        else {
          other.sendBroadcast(msg,sender);
        }
      }
    } else {
      String recp = readString(dis,dis.readInt());
      String msg = readString(dis,dis.readInt());
      send(msg,sender,recp);
    }
  }

  private synchronized void send(String msg, String sender, String receipient) throws IOException {
    byte[] userBytes = sender.getBytes();
    byte[] msgBytes = msg.getBytes();
    byte[] rcpBytes = receipient.getBytes();
    for(ChatManager chat: ChatManager.all){
      if(chat.name.equals(receipient)){
        chat.dos.writeInt(Config.DIRECT_MESSAGE);
        chat.dos.writeInt(userBytes.length);
        chat.dos.write(userBytes);
        chat.dos.writeInt(rcpBytes.length);
        chat.dos.write(rcpBytes);
        chat.dos.writeInt(msgBytes.length);
        chat.dos.write(msgBytes);
        chat.dos.flush();
        return;
      }
    }
    genFail("This user does not exis. ");
  }

  private synchronized void sendBroadcast(String msg, String sender) throws IOException {
    byte[] userBytes = sender.getBytes();
    byte[] msgBytes = msg.getBytes();
    this.dos.writeInt(Config.BROADCAST_MESSAGE);
    this.dos.writeInt(userBytes.length);
    this.dos.write(userBytes);
    this.dos.writeInt(msgBytes.length);
    this.dos.write(msgBytes);
    dos.flush();
  }


  private synchronized void genQueRsp() throws IOException {
    String user = readString(dis,dis.readInt());
    dos.writeInt(Config.QUERY_USER_RESPONSE);
    dos.writeInt(all.size());
    for (ChatManager other : all) {
      byte[] nameBytes = other.name.getBytes();
      int nameSize = nameBytes.length;
      dos.writeInt(nameSize);
      dos.write(nameBytes);
    }
    dos.flush();
  }

  private synchronized void genDiscon() throws IOException {
    // message for connection
    String user = readString(dis,dis.readInt());
    dos.writeInt(Config.DISCONNECT_MESSAGE);
    String msg = "You are no longer connected, " + user;
    byte[] bytes = msg.getBytes();
    int msgLen = bytes.length;
    dos.writeInt(msgLen);
    dos.write(bytes);
    dos.flush();
  }

  private synchronized String genCon() throws IOException {
    // message for connection
    String user = readString(dis,dis.readInt());
    this.name = user;
    dos.writeInt(Config.CONNECT_RESPONSE);
    int total = all.size();
    String msg;
    int status = check();
    if(status == Config.SUCCESS){
      msg = "There are " + total + " other connected users";
      dos.writeBoolean(true);
    }
    else if(status == Config.FULL){
      msg = "Maximum users, cannot add more";
      dos.writeBoolean(false);
      isAlive = false;
    }
    else{
      msg = "User already exists";
      dos.writeBoolean(false);
      isAlive = false;
    }
    byte[] bytes = msg.getBytes();
    int len = bytes.length;
    dos.writeInt(len);
    dos.write(bytes);
    dos.flush();
    return msg;
  }

  private int check() {
    if(ChatManager.all.size() > 10 ){
      return Config.FULL;
    }
    else if(ChatManager.all.contains(this)) {
      return Config.EXIST;
    }
    else return Config.SUCCESS;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ChatManager that = (ChatManager) o;
    return this.name.equals(that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash( name);
  }

  private String readString(DataInputStream dis, int length) throws IOException {
    byte[] bytes = new byte[length];
    dis.readFully(bytes);
    return new String(bytes, StandardCharsets.UTF_8);
  }

  @Override
  public void run() {
    while (isAlive) {
      parseProtocol();
    }
  }
}


