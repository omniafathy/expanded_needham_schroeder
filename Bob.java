public class Bob extends TcpServer {
  public static Integer PORT = 8889;
  public static Integer PORT2 = 8899;
  public boolean port2 = false;

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

  public Bob(boolean ref) {
    if(expanded) {
      proto = new ExpandedNeedhamSchroederServerProtocol();
    }
    else {
      proto = new NeedhamSchroederServerProtocol(ref);
    }
  }


  public Protocol getProtocol() {
    return proto;
  }

  public Integer getPort() {
    if(port2) {
      return PORT2;
    }
    return PORT;
  }
}

