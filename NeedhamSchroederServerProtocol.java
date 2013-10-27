import java.util.ArrayList;

public class NeedhamSchroederServerProtocol extends Protocol {
  private AuthenticationManager authAlice, authKDC;
  private static String KEY_BOB = "DEADBEEFDEADBEEFDEADBEEF";
  private long challenge;
  private State current_state;
  private ArrayList<String> knownClients;
  private boolean disconnect;
  protected enum State { 
    TICKET,
    CHALLENGE,
    AUTHENTICATED
  }

  public NeedhamSchroederServerProtocol() {
    authKDC = new AuthenticationManager(KEY_BOB);
    knownClients = new ArrayList<String>();
    init();
  }

  protected void buildKnownClients() {
    knownClients.add("Alice");
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

  protected void nextState() {
    switch(current_state) {
      case TICKET:
        current_state = State.CHALLENGE;
        break;
      case CHALLENGE:
        current_state = State.AUTHENTICATED;
        break;
      default:
        break;
    }
  }

  public String processInput(String input) {
    String output = null;
    if(current_state == State.TICKET) {
      output = receiveTicket(input);
    }
    else if(current_state == State.CHALLENGE) {
      output = receiveChallenge(input);
    }

    return output;
  }

  protected void init() {
    buildKnownClients();
    current_state = State.TICKET;
    disconnect = false;
  }

  public String receiveChallenge(String input) {
    if(challenge == 0) {
      return null;
    }

    long answer = Long.valueOf(authAlice.decrypt(input.getBytes()));
    if(challenge == answer + 1) {
      nextState();
    }
    else {
      cleanUp();
    }

    return null;
  }

  public String receiveTicket(String input) {
    // ticket i = 0, Kab{N1} i = 1
    String[] request = input.split(",");
    // Kab i = 0, Alice i = 1
    String[] ticket = authKDC.decrypt(request[0].getBytes()).split(",");

    if(!knownClients.contains(ticket[1])) {
      return null;
    }

    authAlice = new AuthenticationManager(ticket[0]);
    long nonce = Long.valueOf(authAlice.decrypt(request[1].getBytes()));
    long answer = nonce - 1;
    challenge = authAlice.getNonce();

    nextState();
    byte[] response = authAlice.encrypt(answer + "," + challenge);
    return new String(response);
  }
}
