package runners;
public class ReflectionRunner {
  public static void main(String[] args) {
    Trudy trudy = new Trudy();
    Bob bob = new Bob(true);
    Thread b = new Thread(bob);

    b.start();
    trudy.start();
  }
}

