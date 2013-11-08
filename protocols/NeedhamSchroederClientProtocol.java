package protocols;
import java.util.ArrayList;
import java.io.*;
import authentication.*;
import util.*;

public class NeedhamSchroederClientProtocol extends Protocol {
  protected AuthenticationManager authBob, authKDC;
  private static String KEY_ALICE = "MMEOWMIXXMEOWMIXXEOWMIXX";
  protected long challenge, challengeKDC;
  protected String challengeServer, nonceChallenge;
  protected State current_state;
  private ArrayList<String> knownHosts;
  protected boolean disconnect;
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
    disconnect = false;
  }

  public boolean disconnect() {
    return disconnect;
  }

  protected void dropConnection() {
    disconnect = true;
  }

  public String getMessage() {
    String output = null;
    switch(current_state) {
      case TICKET:
        challengeKDC = authKDC.getNonce();
        output = challengeKDC + ",Alice/Bob";
        break;
      case CHALLENGE:
        output = challengeServer;
        break;
      default:
        break;
    }

    printOutput("Client", output);
    return output;
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
    printInput("Client", input);
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

    printOutput("Client", output);
    return output;
  }

  protected void init() {
    buildKnownHosts();
    current_state = State.TICKET;
    disconnect = false;
  }

  public String receiveChallenge(String input) {
    try {
      if(challenge == 0) {
        return null;
      }
      // answer i = 0
      // challenge i = 1
      String[] request = authBob.decrypt(Util.toByteArray(input)).split(",");
      long answerFromHost = Long.valueOf(request[0]);

      if(challenge == answerFromHost + 1) {
        nextState();
      }
      else {
        dropConnection();
      }

      long answer = Long.valueOf(request[1]);
      byte[] response = authBob.encrypt(String.valueOf(answer - 1));

      return Util.toHexString(response);
    }
    catch(Exception e) {
      Util.printException("ClientProto#receiveChallenge", e);
      dropConnection();
      return null;
    }
  }

  public String receiveTicket(String input) {
    try {
       // challengeKDC i = 0,
       // Host i = 1
       // Kab i = 2
       // ticket i = 3
      String[] request = authKDC.decrypt(Util.toByteArray(input)).split(",");

      if(!knownHosts.contains(request[1])) {
        dropConnection();
        return null;
      }

      authBob = new AuthenticationManager(request[2]);
      challenge = authBob.getNonce();

      nextState();
      String response = Util.toHexString(authBob.encrypt(String.valueOf(challenge)));
      response = request[3] + "," + response;
      challengeServer = response;
      dropConnection();
      return null;
    }
    catch(Exception e) {
      Util.printException("ClientProto#receiveTicket", e);
      dropConnection();
      return null;
    }
  }
}
