public class Bob extends TcpServer {
  public Protocol getProtocol() {
    return new NeedhamSchroederServerProtocol();
  }

  public static void main(String[] args) {
    Bob bob = new Bob();
    bob.start();
  }
}

