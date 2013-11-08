package protocols;
import authentication.*;

public class NeedhamSchroederReflectionProtocol extends Protocol {
  protected AuthenticationManager authTrudy;
  private static String TICKET = "102D7BD30E9B0849FFE4C64ED708E14FC74D5C6C35B9141626C7BD7C6871BC91";
  private String nonce1 = "978B5B125DFC9137BC46EAB132699EE9BAC392B968578ABF";
  private String nonce2;
  private String nonceAnswer1;
  private String nonceAnswer2;
  protected boolean disconnect;
  protected State current_state;
  protected enum State {
    NONCE1,
    NONCE2,
    AUTHENTICATED
  }

  public NeedhamSchroederReflectionProtocol() {
    init();
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
      case NONCE1:
        output = TICKET + "," + nonce1;
        break;
      case NONCE2:
        output = nonce2;
        break;
      default:
        break;
    }

    printOutput("Trudy", output);
    return output;
  }

  protected void nextState() {
    switch(current_state) {
      case NONCE1:
        current_state = State.NONCE2;
        break;
      case NONCE2:
        current_state = State.AUTHENTICATED;
        break;
      default:
        break;
    }
  }

  public String processInput(String input) {
    String output = null;
    printInput("Trudy", input);
    switch(current_state) {
      case NONCE1:
        output = receiveNonce1(input);
        break;
      case NONCE2:
        output = receiveNonce2(input);
        break;
      default:
        break;
    }

    printOutput("Trudy", output);
    return output;
  }

  protected void init() {
    current_state = State.NONCE1;
    disconnect = false;
  }

  private String receiveNonce1(String input) {
    // Kab{n1} i = 0, Kab{n2} i = 1
    String[] request = input.split(",");
    nonceAnswer1 = request[0];
    nonce2 = request[1];
    nextState();
    // send ticket, nonce2
    return null;
  }
 
  private String receiveNonce2(String input) {
    // Kab{n1} i = 0, Kab{n2} i = 1
    String[] request = input.split(",");
    nonceAnswer2 = request[0];
    nextState();
    // send nonceAnswer2
    return nonceAnswer2;
  }
}
