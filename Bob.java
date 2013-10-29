public class Bob extends TcpServer {
  public static Integer PORT = 8889;
  private Protocol proto = null;

  public Bob() {
    proto = new ExpandedNeedhamSchroederServerProtocol();
  }

  public Protocol getProtocol() {
    return proto;
  }

  public Integer getPort() {
    return PORT;
  }
}

