public class Bob extends TcpServer {
  public static Integer PORT = 8889;
  private Protocol proto = null;
  private boolean expanded = false;

  public Bob() {
    if(expanded) {
      proto = new ExpandedNeedhamSchroederServerProtocol();
    }
    else {
      proto = new NeedhamSchroederServerProtocol();
    }
  }

  public Protocol getProtocol() {
    return proto;
  }

  public Integer getPort() {
    return PORT;
  }
}

