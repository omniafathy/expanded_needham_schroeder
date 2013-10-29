public class ExpandedNeedhamSchroederKdcProtocol extends NeedhamSchroederKdcProtocol {
  public String receiveKeyRequest(String input) {
    // nonce i = 0, Alice/Bob i = 1, nonceChallenge i = 2
    String[] request = input.split(",");
    String[] parties = request[1].split("/");
    String nonceChallenge = authBob.decrypt(Util.toByteArray(request[2]));

    if(!validateParties(parties)) {
      cleanUp();
      return null;
    }

    // Kab i = 0, Alice i = 1, nonceChallenge i = 2
    String ticket = Util.toHexString(authBob.encrypt(KEY_AB + "," + parties[0] + "," + nonceChallenge));
    String message = request[0] + "," + parties[1] + "," +  KEY_AB + "," + ticket; 

    byte[] response = authAlice.encrypt(message);
    return Util.toHexString(response);
  }
}

