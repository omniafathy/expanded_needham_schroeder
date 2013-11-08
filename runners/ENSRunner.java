package runners;
import tcp.clients.*;
import tcp.servers.*;

public class ENSRunner {
  public static void main(String[] args) {
    Alice alice = new Alice();
    Bob bob = new Bob();
    Kdc kdc = new Kdc();

    Thread b = new Thread(bob);
    Thread k = new Thread(kdc);

    b.start();
    k.start();

    alice.start();
  }
}

