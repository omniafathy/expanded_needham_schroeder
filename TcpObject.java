abstract class TcpObject {
  abstract Protocol getProtocol();
  abstract void start();
  protected static Integer port = 8899;
}
