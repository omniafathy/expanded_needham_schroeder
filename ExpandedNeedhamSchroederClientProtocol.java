public class ExpandedNeedhamSchroederClientProtocol extends NeedhamSchroederClientProtocol {
  protected ExpandedState current_state;
  protected enum ExpandedState {
    NONCE_REQUEST,
    TICKET,
    CHALLENGE,
    AUTHENTICATED
  }

  protected void init() {
    buildKnownHosts();
    current_state = ExpandedState.NONCE_REQUEST;
    disconnect = false;
  }

  public String getMessage() {
    String msg = null;
    switch(current_state) {
      case NONCE_REQUEST:
        msg = "I want to talk";
        break;
      case TICKET:
        challengeKDC = authKDC.getNonce();
        msg = challengeKDC + ",Alice/Bob," + nonceChallenge;
        break;
      case CHALLENGE:
        msg = challengeServer;
        break;
      default:
        break;
    }
    printOutput("Client", msg);
    return msg;
  }

  protected void nextState() {
    switch(current_state) {
      case NONCE_REQUEST:
        current_state = ExpandedState.TICKET;
        dropConnection();
        break;
      case TICKET:
        current_state = ExpandedState.CHALLENGE;
        break;
      case CHALLENGE:
        current_state = ExpandedState.AUTHENTICATED;
        break;
      case AUTHENTICATED:
        break;
      default:
        break;
    }
  }

  public String processInput(String input) {
    String output = null;
    printInput("Client", input);
    if(current_state == ExpandedState.NONCE_REQUEST) {
      output = receiveNonceRequest(input);
    }
    else if(current_state == ExpandedState.TICKET) {
      output = receiveTicket(input);
    }
    else if(current_state == ExpandedState.CHALLENGE) {
      output = receiveChallenge(input);
    }

    printOutput("Client", output);
    return output;
  }

  protected String receiveNonceRequest(String input) {
    nonceChallenge = input;
    nextState();
    return null;
  }
}
