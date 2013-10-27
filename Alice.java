import java.util.HashMap;

public class Alice extends TcpClient {
  private static HashMap<String, Integer> hosts;
  private NeedhamSchroederClientProtocol protocol;
  static {
    hosts = new HashMap<String, Integer>();
    buildHosts();
  }
  public Alice() {
    protocol = new NeedhamSchroederClientProtocol();
  }

  private static void buildHosts() {
    hosts.put("Bob", port);
    hosts.put("KDC", port + 1);
  }

  public Protocol getProtocol() {
    return protocol;
  }

  public static void main(String[] args) {
    Alice alice = new Alice();
    alice.start(hosts.get("KDC"));
    alice.start(hosts.get("Bob"));
  }
}

