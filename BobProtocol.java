public class BobProtocol extends Protocol {
  private AuthenticationManager authAlice, authKDC;
  private String keyBob = null, keyAlice = null;
  public BobProtocol() {
    authAlice = new AuthenticationManager(keyAlice);
    authKDC = new AuthenticationManager(keyBob);
  }

  public boolean disconnect() {
    return true;
  }

  public String getMessage() {
    return null;
  }

  public String processInput(String input) {
    return null;
  }
 
}
