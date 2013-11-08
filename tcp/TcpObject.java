package tcp;
import protocols.Protocol;

abstract public class TcpObject {
  abstract protected Protocol getProtocol();
  abstract public Integer getPort();
  abstract public void start();
  protected static String host = "localhost";
}
