abstract class TcpObject {
  abstract Protocol getProtocol();
  abstract void start();
  protected Integer port = 8899;
}
