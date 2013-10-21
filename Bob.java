public class Bob extends TcpServer {
  public Protocol getProtocol() {
    return new BobProtocol();
  }
}

