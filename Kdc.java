public class Kdc extends TcpServer {
  public static Integer PORT = 8899;
  private Protocol proto = null;
  private boolean expanded = false;

  public Kdc() {
    if(expanded) {
      proto = new ExpandedNeedhamSchroederKdcProtocol();
    }
    else {
      proto = new NeedhamSchroederKdcProtocol();
    }
  }

  public Protocol getProtocol() {
    return proto;
  }

  public Integer getPort() {
    return PORT;
  }
}
