public class Kdc extends TcpServer {
  public static Integer PORT = 8899;
  private Protocol proto = null;

  public Kdc() {
    proto = new ExpandedNeedhamSchroederKdcProtocol();
  }

  public Protocol getProtocol() {
    return proto;
  }

  public Integer getPort() {
    return PORT;
  }
}
