abstract class Protocol {
  abstract boolean disconnect();
  abstract String getMessage();
  abstract String processInput(String input); 
  abstract void cleanUp(); 
}
