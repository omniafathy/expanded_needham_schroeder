package protocols;
import java.util.ArrayList;
import authentication.*;
import util.*;

public class NeedhamSchroederServerProtocol extends Protocol {
  protected AuthenticationManager authAlice, authKDC;
  private static String KEY_BOB = "DEADBEEFDEADBEEFDEADBEEF";
  protected long challenge;
  protected State current_state;
  protected ArrayList<String> knownClients;
  protected boolean disconnect, reflection;
  protected String REFLECTION_CLIENT = "Trudy";
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

  public NeedhamSchroederServerProtocol(boolean ref) {
    authKDC = new AuthenticationManager(KEY_BOB);
    knownClients = new ArrayList<String>();
    reflection = ref;
    init();
  }


  protected void buildKnownClients() {
    knownClients.add("Alice");
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
    return null;
  }

  protected void nextState() {
    switch(current_state) {
      case TICKET:
        if(!reflection)
          current_state = State.CHALLENGE;
        break;
      case CHALLENGE:
        current_state = State.AUTHENTICATED;
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
    if(current_state == State.TICKET) {
      output = receiveTicket(input);
    }
    else if(current_state == State.CHALLENGE) {
      output = receiveChallenge(input);
    }

    printOutput("Server", output);
    return output;
  }

  protected void init() {
    buildKnownClients();
    current_state = State.TICKET;
    disconnect = false;
  }

  public String receiveChallenge(String input) {
    try {
      if(challenge == 0) {
        return null;
      }

      long answer = Long.valueOf(authAlice.decrypt(Util.toByteArray(input)));
      if(challenge == answer + 1) {
        nextState();
      }
      else {
        cleanUp();
      }

      return null;
    }
    catch(Exception e) {
      Util.printException("ServerProto#receiveChallenge", e);
      return null;
    }
  }

  public String receiveTicket(String input) {
    try {
      // ticket i = 0, Kab{N1} i = 1
      String[] request = input.split(",");
      // Kab i = 0, Alice i = 1
      String[] ticket = authKDC.decrypt(Util.toByteArray(request[0])).split(",");

      if(!knownClients.contains(ticket[1])) {
        return null;
      } 

      authAlice = new AuthenticationManager(ticket[0]);
      long nonce = Long.valueOf(authAlice.decrypt(Util.toByteArray(request[1])));
      long answer = nonce - 1;
      challenge = authAlice.getNonce();

      if(reflection) {
        // My implementation did not work with NOPADDING so instead I am
        // simulating Trudy being able to read the nonces sent back from
        // bob by sending Kab{n1},Kab{n2}.
        String answerBytes = Util.toHexString(authAlice.encrypt(String.valueOf(answer)));
        String challengeBytes = Util.toHexString(authAlice.encrypt(String.valueOf(challenge)));
        String response = answerBytes + "," + challengeBytes;
        return response;
      }

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
