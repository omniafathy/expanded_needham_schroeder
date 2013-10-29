import java.util.HashMap;

public class Alice extends TcpClient {
  private static HashMap<String, Integer> hosts;
  public static Integer PORT = 8879;
  private NeedhamSchroederClientProtocol protocol;
  static {
    hosts = new HashMap<String, Integer>();
    buildHosts();
  }

  public Alice() {
    protocol = new ExpandedNeedhamSchroederClientProtocol();
  }

  public Integer getPort() {
    return PORT;
  }

  private static void buildHosts() {
    hosts.put("Bob", Bob.PORT);
    hosts.put("KDC", Kdc.PORT);
  }

  public Protocol getProtocol() {
    return protocol;
  }

  public void start() {
    start(hosts.get("Bob"));
    start(hosts.get("KDC"));
    start(hosts.get("Bob"));
  }
}

