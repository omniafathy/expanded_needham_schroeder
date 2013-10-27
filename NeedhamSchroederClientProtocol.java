import java.util.ArrayList;

public class NeedhamSchroederClientProtocol extends Protocol {
  private AuthenticationManager authBob, authKDC;
  private static String KEY_AB = "ALICEWANTSBOB12345678910", KEY_ALICE = "MMEOWMIXXMEOWMIXXEOWMIXX";
  private long challenge, challengeKDC;
  private State current_state;
  private ArrayList<String> knownHosts;
  private boolean disconnect;
  protected enum State { 
    TICKET,
    CHALLENGE,
    AUTHENTICATED
  }

  public NeedhamSchroederClientProtocol() {
    authKDC = new AuthenticationManager(KEY_ALICE);
    knownHosts = new ArrayList<String>();
    init();
  }

  protected void buildKnownHosts() {
    knownHosts.add("Bob");
    knownHosts.add("KDC");
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
    switch(current_state) {
      case TICKET:
        challengeKDC = authKDC.getNonce();
        return challengeKDC + ",Alice/Bob";
      case CHALLENGE:
        break;
      default:
        break;
    }

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
    switch(current_state) {
      case TICKET:
        output = receiveTicket(input);
        break;
      case CHALLENGE:
        output = receiveChallenge(input);
        break;
      default:
        break;
    }

    return output;
  }

  protected void init() {
    buildKnownHosts();
    current_state = State.TICKET;
    disconnect = false;
  }

  public String receiveChallenge(String input) {
    if(challenge == 0) {
      return null;
    }
    // answer i = 0
    // challenge i = 1
    String[] request = authBob.decrypt(input.getBytes()).split(",");
    long answerFromHost = Long.valueOf(request[0]);

    if(challenge == answerFromHost + 1) {
      nextState();
    }
    else {
      dropConnection();
    }

    long answer = Long.valueOf(request[1]);
    byte[] response = authBob.encrypt(String.valueOf(answer - 1));

    return new String(response);
  }

  public String receiveTicket(String input) {
     // challengeKDC i = 0,
     // Host i = 1
     // Kab i = 2
     // ticket i = 3
    String[] request = authKDC.decrypt(input.getBytes()).split(",");

    if(!knownHosts.contains(request[1])) {
      dropConnection();
      return null;
    }

    authBob = new AuthenticationManager(request[2]);
    challenge = authBob.getNonce();

    nextState();
    String response = new String(authBob.encrypt(String.valueOf(challenge)));
    response = request[3] + "," + response;
    return response;
  }
}
