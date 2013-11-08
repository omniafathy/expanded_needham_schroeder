package runners;
public class NSRunner {
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

