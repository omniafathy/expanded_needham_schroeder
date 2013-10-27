public class Kdc extends TcpServer {
  public static Integer PORT = 8899;

  public Protocol getProtocol() {
    return new NeedhamSchroederKdcProtocol();
  }

  public Integer getPort() {
    return PORT;
  }
}
