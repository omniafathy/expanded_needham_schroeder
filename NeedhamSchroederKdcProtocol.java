import java.util.ArrayList;

public class NeedhamSchroederKdcProtocol extends Protocol {
  protected AuthenticationManager authAlice, authBob;
  private static String KEY_BOB = "DEADBEEFDEADBEEFDEADBEEF";
  protected static String KEY_AB = "ALICEWANTSBOB12345678910";
  private static String KEY_ALICE = "MMEOWMIXXMEOWMIXXEOWMIXX";
  private State current_state;
  protected ArrayList<String> knownClients;
  private boolean disconnect;
  protected enum State { 
    KEY_REQUEST
  }

  public NeedhamSchroederKdcProtocol() {
    authAlice = new AuthenticationManager(KEY_ALICE);
    authBob = new AuthenticationManager(KEY_BOB);
    knownClients = new ArrayList<String>();
    init();
  }

  protected void buildKnownClients() {
    knownClients.add("Alice");
    knownClients.add("Bob");
  }

  public void cleanUp() {
    init(); 
  }

  public boolean disconnect() {
    return disconnect;
  }

  protected void dropConnection() {
    disconnect = true;
  }

  public String getMessage() {
    return null;
  }

  public String processInput(String input) {
    printInput("KDC", input);
    String output = receiveKeyRequest(input);
    printOutput("KDC", output);
    return output;
  }

  protected void init() {
    buildKnownClients();
    current_state = State.KEY_REQUEST;
    disconnect = false;
  }

  public String receiveKeyRequest(String input) {
    // nonce i = 0, Alice/Bob i = 1
    String[] request = input.split(",");
    String[] parties = request[1].split("/");

    if(!validateParties(parties)) {
      cleanUp();
      return null;
    }

    // Kab i = 0, Alice i = 1
    String ticket = Util.toHexString(authBob.encrypt(KEY_AB + "," + parties[0]));
    String message = request[0] + "," + parties[1] + "," +  KEY_AB + "," + ticket; 

    byte[] response = authAlice.encrypt(message);
    return Util.toHexString(response);
  }

  protected boolean validateParties(String[] party) {
    for(String p : party) {
      if(!knownClients.contains(p)) {
        return false;
      }
    }

    return true;
  }
}

