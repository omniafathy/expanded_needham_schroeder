public class Kdc extends TcpServer {
  public Protocol getProtocol() {
    return new NeedhamSchroederKdcProtocol();
  }

  public static void main(String[] args) {
    Kdc kdc = new Kdc();
    kdc.start(port + 1);
  }
}
