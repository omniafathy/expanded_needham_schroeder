public class ExpandedNeedhamSchroederServerProtocol extends NeedhamSchroederServerProtocol {
  long nonceChallenge;
  protected ExpandedState current_state;
  protected enum ExpandedState {
    NONCE_REQUEST,
    TICKET,
    CHALLENGE,
    AUTHENTICATED
  }

  protected void init() {
    buildKnownClients();
    current_state = ExpandedState.NONCE_REQUEST;
    disconnect = false;
  }

  protected void nextState() {
    switch(current_state) {
      case NONCE_REQUEST:
        current_state = ExpandedState.TICKET;
        break;
      case TICKET:
        current_state = ExpandedState.CHALLENGE;
        break;
      case CHALLENGE:
        current_state = ExpandedState.AUTHENTICATED;
        dropConnection();
        break;
      case AUTHENTICATED:
        break;
      default:
        break;
    }
  }

  public String processInput(String input) {
    String output = null;
    printInput("Server", input);
    if(current_state == ExpandedState.NONCE_REQUEST) {
      output = receiveNonceRequest(input);
    }
    else if(current_state == ExpandedState.TICKET) {
      output = receiveTicket(input);
    }
    else if(current_state == ExpandedState.CHALLENGE) {
      output = receiveChallenge(input);
    }

    printOutput("Server", output);
    return output;
  }

  protected String receiveNonceRequest(String input) {
    nonceChallenge  = authKDC.getNonce();
    nextState();
    return Util.toHexString(authKDC.encrypt(Long.toString(nonceChallenge)));
  }

  public String receiveTicket(String input) {
    try {
      // ticket i = 0, Kab{N1} i = 1
      String[] request = input.split(",");
      // Kab i = 0, Alice i = 1, nonceChallenge i = 2
      String[] ticket = authKDC.decrypt(Util.toByteArray(request[0])).split(",");

      if(!knownClients.contains(ticket[1])) {
        dropConnection();
        return null;
      }

      if(nonceChallenge != Long.valueOf(ticket[2])) {
        dropConnection();
        return null;
      }
      
      authAlice = new AuthenticationManager(ticket[0]);
      long nonce = Long.valueOf(authAlice.decrypt(Util.toByteArray(request[1])));
      long answer = nonce - 1;
      challenge = authAlice.getNonce();

      nextState();
      byte[] response = authAlice.encrypt(answer + "," + challenge);
      return Util.toHexString(response);
    }
    catch(Exception e) {
      Util.printException("ServerProto#receiveTicket", e);
      return null;
    }
  }
}
